package com.digitalbridge.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;

import com.digitalbridge.DigitalBridgeApplicationTests;
import com.digitalbridge.domain.AssetWrapper;
import com.digitalbridge.domain.FacetDateRange;
import com.digitalbridge.exception.DigitalBridgeException;
import com.digitalbridge.security.SecurityUtils;
import com.google.gson.JsonObject;

import io.searchbox.client.JestResult;
import io.searchbox.core.DocumentResult;

public class ElasticSearchOperationsTests extends DigitalBridgeApplicationTests {

	@Autowired
	ElasticSearchOperations elasticSearchOperations;

	@Test
	public final void testElasticSearchHealth() throws DigitalBridgeException {
		String status = elasticSearchOperations.elasticSearchHealth();
		assertEquals("yellow", status);
	}

	@Test
	public final void testPerformElasticSearch() throws DigitalBridgeException {
		SecurityUtils.runAs(USERNAME, PASSWORD, ROLE_USER);
		Page<AssetWrapper> response = elasticSearchOperations.performElasticSearch();
		assertFalse(response.getContent().isEmpty());
		assertEquals(response.getTotalPages(), 4);
		assertNotNull(response.getContent().get(0).getId());
		assertNotNull(response.getContent().get(0).getAssetName());
		assertNotNull(response.getContent().get(0).getBorough());
		assertNotNull(response.getContent().get(0).getCreatedBy());
		assertNotNull(response.getContent().get(0).getCreatedDate());
		assertNotNull(response.getContent().get(0).getCuisine());
		assertNotNull(response.getContent().get(0).getOrgAssetId());
		assertNotNull(response.getContent().get(0).getAddress());
		assertNotNull(response.getContent().get(0).getAddress().getId());
		assertNotNull(response.getContent().get(0).getAddress().getBuilding());
		assertNotNull(
				response.getContent().get(0).getAddress().getLocation().getCoordinates());
		assertNotNull(response.getContent().get(0).getAddress().getStreet());
		assertNotNull(response.getContent().get(0).getAddress().getZipcode());
		assertNotNull(response.getContent().get(0).getNotes());
		assertNotNull(response.getContent().get(0).getNotes().get(0).getId());
		assertNotNull(response.getContent().get(0).getNotes().get(0).getScore());
		assertNotNull(response.getContent().get(0).getNotes().get(0).getDate());
		assertNotNull(response.getContent().get(0).getNotes().get(0).getNote());
	}

	@Test
	public final void testPerformElasticSearchNoResults() throws DigitalBridgeException {
		SecurityUtils.runAs(USERNAME, PASSWORD, ROLE_USER);
		elasticSearchOperations.performElasticSearch("digitalbridge", "mytype");
	}

	@Test(expected = DigitalBridgeException.class)
	public final void testPerformElasticSearchFail() throws DigitalBridgeException {
		SecurityUtils.runAs(USERNAME, PASSWORD, ROLE_USER);
		elasticSearchOperations.performElasticSearch("MyIndex", "mytype");
	}

	@Test(expected = AccessDeniedException.class)
	public final void testPerformElasticSearchNotAuthenticated()
			throws DigitalBridgeException {
		SecurityUtils.runAs(USERNAME, PASSWORD, "ROLE_INVALID");
		elasticSearchOperations.performElasticSearch();
	}

	@Test(expected = AuthenticationCredentialsNotFoundException.class)
	public final void testPerformElasticSearchNoCredentials()
			throws DigitalBridgeException {
		elasticSearchOperations.performElasticSearch();
	}

	@Test
	public final void testTermFacetSearchWithOutFilters()
			throws DigitalBridgeException, IOException {
		SecurityUtils.runAs(USERNAME, PASSWORD, ROLE_USER);
		Map<String, Map<String, Long>> response = elasticSearchOperations
				.termFacetSearch(Collections.<String, Object[]>emptyMap(), true);
		assertTrue(!response.isEmpty());
		assertTrue(response.size() == 3);
	}

	@Test
	public final void testTermFacetSearchWithFilters()
			throws DigitalBridgeException, IOException {
		SecurityUtils.runAs(USERNAME, PASSWORD, ROLE_USER);
		Map<String, Object[]> termFilters = new HashMap<String, Object[]>();
		termFilters.put("cuisine", new String[] { "indian" });
		termFilters.put("borough", new String[] { "manhattan" });
		FacetDateRange facetDateRange = new FacetDateRange();
		facetDateRange.setStartDate(DateTime.parse("2014-12-01T00:00:00.000Z",
				ISODateTimeFormat.dateTimeParser().withOffsetParsed()));
		facetDateRange.setEndDate(DateTime.parse("2014-12-31T23:59:59.999Z",
				ISODateTimeFormat.dateTimeParser().withOffsetParsed()));
		FacetDateRange facetDateRange1 = new FacetDateRange();
		facetDateRange1.setStartDate(DateTime.parse("2015-04-01T00:00:00.000Z",
				ISODateTimeFormat.dateTimeParser().withOffsetParsed()));
		facetDateRange1.setEndDate(DateTime.parse("2015-04-30T23:59:59.999Z",
				ISODateTimeFormat.dateTimeParser().withOffsetParsed()));
		termFilters.put("lDate", new Object[] { facetDateRange, facetDateRange1 });
		Map<String, Map<String, Long>> response = elasticSearchOperations
				.termFacetSearch(termFilters, true);
		assertTrue(!response.isEmpty());
		assertTrue(response.size() == 3);
	}

	@Test
	public final void testOptimizeIndex() throws DigitalBridgeException {
		elasticSearchOperations.optimizeIndex();
	}

	@Test(expected = AuthenticationCredentialsNotFoundException.class)
	public final void testRefreshIndexNoCredentials() throws DigitalBridgeException {
		JestResult response = elasticSearchOperations.refreshIndex(null);
		assertTrue(response.isSucceeded());
		JestResult response1 = elasticSearchOperations.refreshIndex("digitalbridge");
		assertTrue(response1.isSucceeded());
	}

	@Test
	public final void testRefreshIndex() throws DigitalBridgeException {
		SecurityUtils.runAs(USERNAME, PASSWORD, ROLE_USER);
		JestResult response = elasticSearchOperations.refreshIndex("");
		assertTrue(response.isSucceeded());
		JestResult response1 = elasticSearchOperations.refreshIndex("digitalbridge");
		assertTrue(response1.isSucceeded());
	}

	@Test
	public final void testElasticSearchStats() throws DigitalBridgeException {
		SecurityUtils.runAs(USERNAME, PASSWORD, ROLE_ADMIN);
		JsonObject response = elasticSearchOperations.elasticSearchStats();
		assertNotNull(response);
	}

	@Test
	public final void testDropIndexes() throws DigitalBridgeException {
		SecurityUtils.runAs(USERNAME, PASSWORD, ROLE_ADMIN);
		JestResult res = elasticSearchOperations.createIndexes(USERNAME);
		if (res.isSucceeded()) {
			DocumentResult response = elasticSearchOperations.dropIndexes(USERNAME, null);
			assertNotNull(response);
			assertTrue(response.isSucceeded());
		}
		assertTrue(res.isSucceeded());
	}

}
