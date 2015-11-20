package com.digitalbridge.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.bson.Document;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.data.domain.Page;

import com.digitalbridge.DigitalBridgeApplicationTests;
import com.digitalbridge.domain.Address;
import com.digitalbridge.domain.AssetWrapper;
import com.digitalbridge.security.SecurityUtils;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.IndexOptions;

public class AddressServiceTests extends DigitalBridgeApplicationTests {

	@Test
	public final void testUpdateSetValue() {

		SecurityUtils.runAs(USERNAME, PASSWORD, ROLE_USER);
		Map<String, Object> value = new HashMap<String, Object>();
		int random = new Random().nextInt(1000);
		value.put("building", random);
		Address address = addressService.updateSetValue(assetID, value);
		assertTrue(address.getBuilding().equalsIgnoreCase(String.valueOf(random)));
		Page<AssetWrapper> res = assetWrapperService
				.findByAddressIdIn(Arrays.asList(address.getId()), pageable);
		assertEquals(assetID, res.getContent().get(0).getId());

	}

	@Test
	@Ignore
	public void testCreateIndex() throws Exception {
		MongoCollection<Document> collection = mongoClient.getDatabase("digitalbridge")
				.getCollection("address", Document.class);
		Document bkey = Document.parse("{ \"location\": \"2dsphere\" }");
		collection.dropIndexes();
		collection.createIndex(bkey, new IndexOptions().name("geospatialIdx"));
	}

}
