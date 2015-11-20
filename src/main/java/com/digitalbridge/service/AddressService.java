package com.digitalbridge.service;

import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.access.annotation.Secured;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.digitalbridge.domain.Address;
import com.digitalbridge.mongodb.repository.AssetWrapperRepository;


/**
 * <p>AddressService class.</p>
 *
 * @author rajakolli
 * @version 1:0
 */
@RestController
@RequestMapping(value = "/assetwrapper/search")
public class AddressService {
	private static final Logger LOGGER = LoggerFactory.getLogger(AddressService.class);

	@Autowired AssetWrapperRepository assetWrapperRepository;
	@Autowired MongoTemplate mongoTemplate;

	/**
	 * <p>updateSetValue.</p>
	 *
	 * @param assetID a {@link java.lang.String} object.
	 * @param value a {@link java.util.Map} object.
	 * @return a {@link com.digitalbridge.domain.Address} object.
	 */
	@Secured({ "ROLE_USER" })
	@RequestMapping(value = "/update/addressValue", method = { RequestMethod.GET, RequestMethod.PUT },
      headers = "Accept=application/json")
	public Address updateSetValue(String assetID, Map<String, Object> value) {
		LOGGER.debug("received update request for assetID {}", assetID);
		String addressID = assetWrapperRepository.findOne(assetID).getAddress().getId();
		Query query = new Query(Criteria.where("_id").is(addressID));
		Update update = new Update();
		for (Entry<String, Object> entry : value.entrySet()) {
			update.set(entry.getKey(), entry.getValue());
		}
		Address result = mongoTemplate.findAndModify(query, update,
				new FindAndModifyOptions().returnNew(true).upsert(false), Address.class);
		LOGGER.debug("Result : {}", result);
		Assert.isTrue(result.getId().equalsIgnoreCase(addressID),"Address Matched");
		return result;
	}
}
