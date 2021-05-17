package com.digitalbridge.service;

import java.util.*;

import org.bson.Document;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;

import com.digitalbridge.DigitalBridgeApplicationTests;
import com.digitalbridge.domain.Address;
import com.digitalbridge.domain.AssetWrapper;
import com.digitalbridge.security.SecurityUtils;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.IndexOptions;

import static org.assertj.core.api.Assertions.assertThat;

public class AddressServiceTests extends DigitalBridgeApplicationTests {

	@Test
	public final void testUpdateSetValue() {

		SecurityUtils.runAs(USERNAME, PASSWORD, ROLE_USER);
		Map<String, Object> value = new HashMap<String, Object>();
		int random = new Random().nextInt(1000);
		value.put("building", random);
		Address address = addressService.updateSetValue(assetID, value);
		assertThat(address.getBuilding()).isEqualToIgnoringCase(String.valueOf(random));
		Page<AssetWrapper> res = assetWrapperService
				.findByAddressIdIn(Collections.singletonList(address.getId()), pageable);
		assertThat(assetID).isEqualTo(res.getContent().get(0).getId());

	}

	@Test
	@Disabled
	public void testCreateIndex() throws Exception {
		MongoCollection<Document> collection = mongoClient.getDatabase("digitalbridge")
				.getCollection("address", Document.class);
		Document bkey = Document.parse("{ \"location\": \"2dsphere\" }");
		collection.dropIndexes();
		collection.createIndex(bkey, new IndexOptions().name("geospatialIdx"));
	}

}
