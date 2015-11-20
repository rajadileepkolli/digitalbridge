package com.digitalbridge.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;

import com.digitalbridge.DigitalBridgeApplicationTests;
import com.digitalbridge.domain.AssetWrapper;
import com.digitalbridge.domain.Notes;
import com.digitalbridge.security.SecurityUtils;

public class AssetWrapperServiceTests extends DigitalBridgeApplicationTests {

	@Test
	public final void testGetAll() {
		SecurityUtils.runAs(USERNAME, PASSWORD, ROLE_USER);
		assetWrapperService.getAll();
	}

	@Test
	public final void testGeospatialsearch() {
		SecurityUtils.runAs(USERNAME, PASSWORD, ROLE_USER);
		Page<AssetWrapper> response = assetWrapperService.geospatialsearch();
		assertTrue(response.hasContent());
	}

	@Test
	public final void testCreateGeoSpatialIndex() {
		SecurityUtils.runAs(USERNAME, PASSWORD, ROLE_ADMIN);
		assetWrapperService.createGeoSpatialIndex();
	}

	@Test
	public void testSetUpdateValue() throws Exception {
		SecurityUtils.runAs(USERNAME, PASSWORD, ROLE_USER);
		Map<String, Object> value = new HashMap<String, Object>();
		value.put("aName", "Matt'S Grill Restaurant");
		AssetWrapper assetWrapper = assetWrapperService.updateField(assetID, value);
		assertTrue(assetWrapper.getId().equalsIgnoreCase(assetID));
	}

	@Test
	public void testAddNotesandDeleteNotes() {
		SecurityUtils.runAs(USERNAME, PASSWORD, ROLE_USER);
		Map<String, Object> value = new HashMap<String, Object>();
		Notes notes = new Notes("R", new Date(1441712050500l), 21);
		notesRepository.save(notes);
		value.put("notes", notes);
		SecurityUtils.runAs("system", "system", "ROLE_USER");
		AssetWrapper assetWrapper = assetWrapperRepository.findOne(assetID);
		int originalCount = assetWrapper.getNotes().size();
		AssetWrapper restaurants = assetWrapperService.addToFieldArray(assetID, value);
		assertTrue(restaurants.getId().equalsIgnoreCase(assetID));
		assertEquals(assetWrapperRepository.findOne(assetID).getNotes().size(),
				originalCount + 1);
		assetWrapperService.removeFromFieldArray(assetID, value);
		SecurityContextHolder.clearContext();
		assertEquals(assetWrapperRepository.findOne(assetID).getNotes().size(),
				originalCount);
	}

}
