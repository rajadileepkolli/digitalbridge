package com.digitalbridge.service;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.junit.jupiter.api.Test;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ElasticSearchOperationsTests extends DigitalBridgeApplicationTests {

	@Autowired
	ElasticSearchOperations elasticSearchOperations;

	@Test
	public final void testElasticSearchHealth() throws DigitalBridgeException {
		String status = elasticSearchOperations.elasticSearchHealth();
		assertThat(status).isEqualTo("yellow");
	}

	@Test
	public final void testPerformElasticSearch() throws DigitalBridgeException {
		SecurityUtils.runAs(USERNAME, PASSWORD, ROLE_USER);
		Page<AssetWrapper> response = elasticSearchOperations.performElasticSearch();
		assertThat(response.getContent()).isNotEmpty();
		assertThat(response.getTotalPages()).isEqualTo(4);
		assertThat(response.getContent().get(0).getId()).isNotNull();
		assertThat(response.getContent().get(0).getAssetName()).isNotNull();
		assertThat(response.getContent().get(0).getBorough()).isNotNull();
		assertThat(response.getContent().get(0).getCreatedBy()).isNotNull();
		assertThat(response.getContent().get(0).getCreatedDate()).isNotNull();
		assertThat(response.getContent().get(0).getCuisine()).isNotNull();
		assertThat(response.getContent().get(0).getOrgAssetId()).isNotNull();
		assertThat(response.getContent().get(0).getAddress()).isNotNull();
		assertThat(response.getContent().get(0).getAddress().getId()).isNotNull();
		assertThat(response.getContent().get(0).getAddress().getBuilding()).isNotNull();
		assertThat(
				response.getContent().get(0).getAddress().getLocation().getCoordinates()).isNotNull();
		assertThat(response.getContent().get(0).getAddress().getStreet()).isNotNull();
		assertThat(response.getContent().get(0).getAddress().getZipcode()).isNotNull();
		assertThat(response.getContent().get(0).getNotes()).isNotNull();
		assertThat(response.getContent().get(0).getNotes().get(0).getId()).isNotNull();
		assertThat(response.getContent().get(0).getNotes().get(0).getScore()).isNotNull();
		assertThat(response.getContent().get(0).getNotes().get(0).getDate()).isNotNull();
		assertThat(response.getContent().get(0).getNotes().get(0).getNote()).isNotNull();
	}

	@Test
	public final void testPerformElasticSearchNoResults() throws DigitalBridgeException {
		SecurityUtils.runAs(USERNAME, PASSWORD, ROLE_USER);
		elasticSearchOperations.performElasticSearch("digitalbridge", "mytype");
	}

	@Test
	public final void testPerformElasticSearchFail() {
		SecurityUtils.runAs(USERNAME, PASSWORD, ROLE_USER);
		assertThatThrownBy(() -> elasticSearchOperations.performElasticSearch("MyIndex", "mytype"))
				.isInstanceOf(DigitalBridgeException.class);
	}

	@Test
	public final void testPerformElasticSearchNotAuthenticated() {
		SecurityUtils.runAs(USERNAME, PASSWORD, "ROLE_INVALID");

		assertThatThrownBy(() -> elasticSearchOperations.performElasticSearch())
				.isInstanceOf(AccessDeniedException.class);
	}

	@Test
	public final void testPerformElasticSearchNoCredentials() {
		assertThatThrownBy(() -> elasticSearchOperations.performElasticSearch())
				.isInstanceOf(AuthenticationCredentialsNotFoundException.class);
	}

	@Test
	public final void testTermFacetSearchWithOutFilters()
			throws DigitalBridgeException {
		SecurityUtils.runAs(USERNAME, PASSWORD, ROLE_USER);
		Map<String, Map<String, Long>> response = elasticSearchOperations
				.termFacetSearch(Collections.emptyMap(), true);
		assertThat(response).isNotEmpty();
		assertThat(response.size()).isEqualTo(3);
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
		assertThat(response).isNotEmpty();
		assertThat(response.size()).isEqualTo(3);
	}

	@Test
	public final void testOptimizeIndex() throws DigitalBridgeException {
		elasticSearchOperations.optimizeIndex();
	}

//	@Test(expected = AuthenticationCredentialsNotFoundException.class)
	@Test
	public final void testRefreshIndexNoCredentials() throws DigitalBridgeException {
		JestResult response = elasticSearchOperations.refreshIndex(null);
		assertThat(response.isSucceeded()).isTrue();
		JestResult response1 = elasticSearchOperations.refreshIndex("digitalbridge");
		assertThat(response1.isSucceeded()).isTrue();
	}

	@Test
	public final void testRefreshIndex() throws DigitalBridgeException {
		SecurityUtils.runAs(USERNAME, PASSWORD, ROLE_USER);
		JestResult response = elasticSearchOperations.refreshIndex("");
		assertThat(response.isSucceeded()).isTrue();
		JestResult response1 = elasticSearchOperations.refreshIndex("digitalbridge");
		assertThat(response1.isSucceeded()).isTrue();
	}

	@Test
	public final void testElasticSearchStats() throws DigitalBridgeException {
		SecurityUtils.runAs(USERNAME, PASSWORD, ROLE_ADMIN);
		JsonObject response = elasticSearchOperations.elasticSearchStats();
		assertThat(response).isNotNull();
	}

	@Test
	public final void testDropIndexes() throws DigitalBridgeException {
		SecurityUtils.runAs(USERNAME, PASSWORD, ROLE_ADMIN);
		JestResult res = elasticSearchOperations.createIndexes(USERNAME);
		if (res.isSucceeded()) {
			DocumentResult response = elasticSearchOperations.dropIndexes(USERNAME, null);
			assertThat(response).isNotNull();
			assertThat(response.isSucceeded()).isTrue();
		}
		assertThat(res.isSucceeded()).isTrue();
	}

}
