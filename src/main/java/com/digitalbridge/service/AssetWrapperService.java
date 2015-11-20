package com.digitalbridge.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.digitalbridge.domain.Address;
import com.digitalbridge.domain.AssetWrapper;
import com.digitalbridge.mongodb.repository.AddressRepository;
import com.digitalbridge.mongodb.repository.AssetWrapperRepository;
import com.digitalbridge.util.Constants;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoException;

/**
 * <p> AssetWrapperService class. </p>
 *
 * @author rajakolli
 * @version 1: 0
 */

@RestController
@RequestMapping(value = "/assetwrapper/search")
public class AssetWrapperService {

  private static final Logger LOGGER = LoggerFactory.getLogger(AssetWrapperService.class);

  @Autowired AssetWrapperRepository assetWrapperRepository;

  @Autowired AddressRepository addressRepository;

  @Autowired MongoTemplate mongoTemplate;

  /**
   * <p>
   * getAll.
   * </p>
   *
   * @return a {@link org.springframework.data.domain.Page} object.
   */
  @Secured({ "ROLE_USER" })
  @RequestMapping(method = RequestMethod.GET)
  public Page<AssetWrapper> getAll() {
    return assetWrapperRepository.findAll(new PageRequest(Constants.ZERO, Constants.PAGESIZE));
  }

  /**
   * <p>
   * geospatialsearch.
   * </p>
   *
   * @return a {@link org.springframework.data.domain.Page} object.
   */
  @Secured({ "ROLE_USER" })
  @RequestMapping(value = "/geospatialsearch")
  public Page<AssetWrapper> geospatialsearch() {
    Point point = new Point(-74.0014541, 40.7408231);
    Distance distance = new Distance(1, Metrics.MILES);
    Pageable pageable = new PageRequest(Constants.ZERO, Constants.PAGESIZE);
    List<Address> result = addressRepository.findByLocationNear(point, distance, pageable);
    List<String> addressIds = new ArrayList<String>(result.size());
    for (Address address : result) {
      addressIds.add(address.getId());
    }
    return findByAddressIdIn(addressIds, pageable);
  }

  /**
   * <p>
   * updateValue.
   * </p>
   *
   * @param assetID a {@link java.lang.String} object.
   * @return a {@link com.digitalbridge.domain.AssetWrapper} object.
   * @param mapvalue a {@link java.util.Map} object.
   */
  @Secured({ "ROLE_USER" })
  @RequestMapping(value = "/update/arrayvalue", method = { RequestMethod.GET, RequestMethod.PUT },
      headers = "Accept=application/json")
  public AssetWrapper updateField(@RequestParam("assetID") String assetID,
      @RequestParam("value") Map<String, Object> mapvalue) {
    LOGGER.debug("received update request for assetID {}", assetID);
    Query query = new Query(Criteria.where("_id").is(assetID));
    Update update = new Update();
    for (Entry<String, Object> entry : mapvalue.entrySet()) {
      update.set(entry.getKey(), entry.getValue());
    }

    AssetWrapper result = mongoTemplate.findAndModify(query, update,
        new FindAndModifyOptions().returnNew(true).upsert(true), AssetWrapper.class);
    LOGGER.debug("Result : {}", result);
    return result;
  }

  /**
   * <p>
   * updateArrayValue.
   * </p>
   *
   * @param assetID a {@link java.lang.String} object.
   * @param map a {@link java.util.Map} object.
   * @return a {@link com.digitalbridge.domain.AssetWrapper} object.
   */
  @Secured({ "ROLE_USER" })
  @RequestMapping(value = "/update/updateArrayValue", method = { RequestMethod.PUT, RequestMethod.GET },
      headers = "Accept=application/json")
  public AssetWrapper addToFieldArray(@RequestParam("assetID") String assetID,
      @RequestParam("value") Map<String, Object> map) {
    LOGGER.debug("received update request for assetID {}", assetID);
    Query query = new Query(Criteria.where("id").is(assetID));
    Update update = new Update();
    for (Entry<String, Object> entry : map.entrySet()) {
      update.push(entry.getKey(), entry.getValue());
    }
    AssetWrapper result = mongoTemplate.findAndModify(query, update,
        new FindAndModifyOptions().returnNew(true).upsert(false), AssetWrapper.class);
    LOGGER.debug("Result : {}", result);
    return result;
  }

  /**
   * <p>
   * updateRemoveValue.
   * </p>
   *
   * @param assetID a {@link java.lang.String} object.
   * @param map a {@link java.util.Map} object.
   * @return a {@link com.digitalbridge.domain.AssetWrapper} object.
   */
  @Secured({ "ROLE_USER" })
  @RequestMapping(value = "/update/removeFromFieldArray", method = { RequestMethod.PUT, RequestMethod.GET },
      headers = "Accept=application/json")
  public AssetWrapper removeFromFieldArray(@RequestParam("assetID") String assetID,
      @RequestParam("value") Map<String, Object> map) {
    LOGGER.debug("received update request for assetID {}", assetID);
    Query query = new Query(Criteria.where("id").is(assetID));

    Update update = new Update();
    for (Entry<String, Object> entry : map.entrySet()) {
      update.pull(entry.getKey(), entry.getValue());
    }
    AssetWrapper result = mongoTemplate.findAndModify(query, update,
        new FindAndModifyOptions().returnNew(true).upsert(false), AssetWrapper.class);
    LOGGER.debug("Result : {}", result);
    return result;
  }

  /**
   * <p>
   * createGeoSpatialIndex.
   * </p>
   */
  @Secured({ "ROLE_ADMIN" })
  @RequestMapping(value = "/createGeoSpatialIndex")
  public void createGeoSpatialIndex() {
    DBCollection collection = mongoTemplate.getCollection("address");
    DBObject key = new BasicDBObject("location", "2dsphere");
    collection.dropIndexes();
    try {
      collection.createIndex(key, "geospatialIdx");
    } catch (MongoException e) {
      LOGGER.error("MongoException :: {}", e.getMessage(), e);
    }
  }

  /**
   * <p>
   * findByAddressIdIn.
   * </p>
   *
   * @param asList a {@link java.util.List} object.
   * @param pageable a {@link org.springframework.data.domain.Pageable} object.
   * @return a {@link org.springframework.data.domain.Page} object.
   */
  @Secured({ "ROLE_USER" })
  @RequestMapping(value = "/findByAddressIdIn")
  public Page<AssetWrapper> findByAddressIdIn(List<String> asList, Pageable pageable) {

    final List<ObjectId> idList = new ArrayList<ObjectId>(asList.size());

    for (final String maker : asList) {
      idList.add(new ObjectId(maker));
    }
    Query query = new Query(Criteria.where("address.$id").in(idList));
    List<AssetWrapper> result = mongoTemplate.find(query, AssetWrapper.class);
    return new PageImpl<AssetWrapper>(result, pageable, result.size());
  }

}
