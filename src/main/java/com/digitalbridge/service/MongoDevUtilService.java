package com.digitalbridge.service;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.bson.Document;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.access.annotation.Secured;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digitalbridge.domain.Address;
import com.digitalbridge.domain.AssetWrapper;
import com.digitalbridge.domain.Notes;
import com.digitalbridge.mongodb.repository.AddressRepository;
import com.digitalbridge.mongodb.repository.AssetWrapperRepository;
import com.digitalbridge.mongodb.repository.NotesRepository;
import com.digitalbridge.util.Constants;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.MongoException;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * <p> MongoDevUtilService class. </p>
 *
 * @author rajakolli
 * @version 1: 0
 */
@RequestMapping(value = "/assetwrapper/search")
@RestController
public class MongoDevUtilService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MongoDevUtilService.class);

	@Autowired AssetWrapperRepository assetWrapperRepository;
	@Autowired AddressRepository addressRepository;
	@Autowired NotesRepository notesRepository;
	
	@Autowired MongoTemplate mongoTemplate;

	/**
	 * <p>
	 * extractRestaurants.
	 * </p>
	 *
	 * @return a {@link java.lang.String} object.
	 * @throws java.lang.InterruptedException if any.
	 */
	@RequestMapping(value = "/extractRestaurants")
	@Secured({ "ROLE_USER" })
	public String extractRestaurants() throws InterruptedException {
		List<MongoCredential> credentialsList = new ArrayList<>();
		credentialsList.add(MongoCredential.createCredential("testAdmin", "test", "password".toCharArray()));
		ServerAddress addr = new ServerAddress(new InetSocketAddress(Constants.LOCALHOST,Constants.PRIMARYPORT));
		MongoClient mongoClient = new MongoClient(addr, credentialsList);
		final MongoDatabase database = mongoClient.getDatabase("test");
		MongoCollection<Document> collection = database.getCollection("restaurants", Document.class);
		List<Document> res = collection.find().into(new ArrayList<Document>());

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		transformAndInsert(res);
		mongoClient.close();
		stopWatch.stop();
		return "Inserted " + assetWrapperRepository.count() + " documents in " + stopWatch.getTotalTimeSeconds() + " sec";
	}

	@SuppressWarnings("unchecked")
	private void transformAndInsert(List<Document> resultList) throws InterruptedException {
		addressRepository.deleteAll();
		notesRepository.deleteAll();
		assetWrapperRepository.deleteAll();
		List<AssetWrapper> assetWrapperList = new ArrayList<AssetWrapper>();
		for (Document res : resultList) {
			AssetWrapper assetwrapper = new AssetWrapper();
			assetwrapper.setId(res.get("_id").toString());
			Address address = new Address();
			Document addressDocument = (Document) res.get("address");
			address.setBuilding(addressDocument.getString("building"));
			String val = addressDocument.get("coord").toString().replace("[", "").replace("]", "");
			if (val != null && val.length() > 0) {
				Point point = new Point(Double.valueOf(val.split(",")[0]), Double.valueOf(val.split(",")[1]));
				address.setLocation(new GeoJsonPoint(point));
			}
			address.setStreet(addressDocument.getString("street"));
			address.setZipcode(addressDocument.getString("zipcode"));
			assetwrapper.setAddress(address);
			assetwrapper.setBorough(res.getString("borough"));
			assetwrapper.setCuisine(res.getString("cuisine"));
			List<Notes> notesList = new ArrayList<Notes>();
			List<Document> var = (List<Document>) res.get("grades");
			for (Document notesDocument : var) {
				Notes notes = new Notes();
				notes.setNote(notesDocument.getString("grade"));
				notes.setScore(notesDocument.getInteger("score"));
				notes.setDate(notesDocument.getDate("date"));
				notesList.add(notes);
			}
			assetwrapper.setNotes(notesList);
			assetwrapper.setAssetName(res.getString("name"));
			assetwrapper.setOrgAssetId(res.getString("restaurant_id"));
			/*try{
				addressRepository.save(address);
				assetWrapperRepository.save(assetwrapper);
			}catch(Exception e)
			{
				e.getStackTrace();
			}
			Thread.sleep(500);*/
			assetWrapperList.add(assetwrapper);
		}
		try {
			assetWrapperRepository.save(assetWrapperList);
		} catch (MongoException e) {
			LOGGER.error("Exception :{}", e.getMessage(), e);
		}
	}

	/**
	 * <p>updateDate.</p>
	 */
	@RequestMapping(value = "/updateDate")
	public void updateDate() {
		int i = Constants.ZERO;
		Page<AssetWrapper> all = null;
		do {
			all = assetWrapperRepository.findAll(new PageRequest(i, Constants.PAGESIZE));
			for (AssetWrapper assetWrapper : all) {
				if (Integer.parseInt(assetWrapper.getOrgAssetId()) % Constants.TWELVE == Constants.ONE) {
					Date value = new DateTime().minusMonths(Constants.ONE).toDate();
					updateCreatedValue(assetWrapper, value);
				} else if (Integer.parseInt(assetWrapper.getOrgAssetId()) % Constants.TWELVE == Constants.TWO) {
					Date value = new DateTime().minusMonths(Constants.TWO).toDate();
					updateCreatedValue(assetWrapper, value);
				} else if (Integer.parseInt(assetWrapper.getOrgAssetId()) % Constants.TWELVE == Constants.THREE) {
					Date value = new DateTime().minusMonths(Constants.THREE).toDate();
					updateCreatedValue(assetWrapper, value);
				} else if (Integer.parseInt(assetWrapper.getOrgAssetId()) % Constants.TWELVE == 4) {
					Date value = new DateTime().minusMonths(4).toDate();
					updateCreatedValue(assetWrapper, value);
				} else if (Integer.parseInt(assetWrapper.getOrgAssetId()) % Constants.TWELVE == 5) {
					Date value = new DateTime().minusMonths(5).toDate();
					updateCreatedValue(assetWrapper, value);
				} else if (Integer.parseInt(assetWrapper.getOrgAssetId()) % Constants.TWELVE == 6) {
					Date value = new DateTime().minusMonths(6).toDate();
					updateCreatedValue(assetWrapper, value);
				} else if (Integer.parseInt(assetWrapper.getOrgAssetId()) % Constants.TWELVE == 7) {
					Date value = new DateTime().minusMonths(7).toDate();
					updateCreatedValue(assetWrapper, value);
				} else if (Integer.parseInt(assetWrapper.getOrgAssetId()) % Constants.TWELVE == 8) {
					Date value = new DateTime().minusMonths(8).toDate();
					updateCreatedValue(assetWrapper, value);
				} else if (Integer.parseInt(assetWrapper.getOrgAssetId()) % Constants.TWELVE == 9) {
					Date value = new DateTime().minusMonths(9).toDate();
					updateCreatedValue(assetWrapper, value);
				} else if (Integer.parseInt(assetWrapper.getOrgAssetId()) % Constants.TWELVE == 10) {
					Date value = new DateTime().minusMonths(10).toDate();
					updateCreatedValue(assetWrapper, value);
				} else if (Integer.parseInt(assetWrapper.getOrgAssetId()) % Constants.TWELVE == 11) {
					Date value = new DateTime().minusMonths(11).toDate();
					updateCreatedValue(assetWrapper, value);
				}
			}
			i++;
		} while (all.hasNext());
	}

	private void updateCreatedValue(AssetWrapper assetWrapper, Date value) {
		Query query = new Query(Criteria.where("_id").is(assetWrapper.getId()));
		Update update = new Update().set("lDate", value).set("lastModifiedBy", "appAdmin");
		mongoTemplate.updateFirst(query, update, AssetWrapper.class);
	}

	/**
	 * <p>
	 * dropCollection.
	 * </p>
	 *
	 * @param collection a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 */
	@RequestMapping(value = "/dropCollection/{id}")
	public String dropCollection(@PathVariable("id") @NotNull String collection) {
		mongoTemplate.dropCollection(collection);
		return "Dropped Collection :{ }" + collection;
	}

	/**
	 * <p>
	 * verifySetMembers.
	 * </p>
	 *
	 * @throws java.lang.Exception if any.
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/replicaStatus")
	public void verifySetMembers() throws Exception {

		List<MongoCredential> credentialsList = new ArrayList<MongoCredential>();
		credentialsList.add(MongoCredential.createCredential("appAdmin", "admin", "password".toCharArray()));
		ServerAddress addr = new ServerAddress(new InetSocketAddress(Constants.LOCALHOST,Constants.TERITORYPORT));
		MongoClientOptions mongoClientOptions = MongoClientOptions.builder()
				.readPreference(ReadPreference.secondaryPreferred()).build();
		MongoClient mongo = new MongoClient(addr, credentialsList, mongoClientOptions);
		// mongo.slaveOk();
		// mongo.getDatabase("deloitte").getCollection("assetwrapper").drop();
		final Document result = mongo.getDatabase("admin").runCommand(new Document("replSetGetStatus", 1));

		final List<Document> members = (List<Document>) result.get("members");

		for (final Document member : members) {
			LOGGER.info(member.toJson());
		}
		mongo.close();
	}

}
