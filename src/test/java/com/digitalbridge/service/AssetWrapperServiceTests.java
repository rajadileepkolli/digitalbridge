package com.digitalbridge.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;

import com.digitalbridge.DigitalBridgeApplicationTests;
import com.digitalbridge.domain.AssetWrapper;
import com.digitalbridge.domain.Notes;
import com.digitalbridge.security.SecurityUtils;

import static org.assertj.core.api.Assertions.assertThat;

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
		assertThat(response.hasContent()).isTrue();
	}

	@Test
	@Disabled
	public final void testCreateGeoSpatialIndex() {
		SecurityUtils.runAs(USERNAME, PASSWORD, ROLE_ADMIN);
//		assetWrapperService.createGeoSpatialIndex();
	}

	@Test
	public void testSetUpdateValue() throws Exception {
		SecurityUtils.runAs(USERNAME, PASSWORD, ROLE_USER);
		Map<String, Object> value = new HashMap<String, Object>();
		value.put("aName", "Matt'S Grill Restaurant");
		AssetWrapper assetWrapper = assetWrapperService.updateField(assetID, value);
		assertThat(assetWrapper.getId()).isEqualToIgnoringCase(assetID);
	}

	@Test
	public void testAddNotesandDeleteNotes() {
		SecurityUtils.runAs(USERNAME, PASSWORD, ROLE_USER);
		Map<String, Object> value = new HashMap<String, Object>();
		Notes notes = new Notes("R", new Date(1441712050500l), 21);
		notesRepository.save(notes);
		value.put("notes", notes);
		AssetWrapper assetWrapper = assetWrapperRepository.findById(assetID).get();
		int originalCount = assetWrapper.getNotes().size();
		AssetWrapper restaurants = assetWrapperService.addToFieldArray(assetID, value);
		assertThat(restaurants.getId()).isEqualToIgnoringCase(assetID);
		assertThat(assetWrapperRepository.findById(assetID).get().getNotes().size()).isEqualTo(
				originalCount + 1);
		assetWrapperService.removeFromFieldArray(assetID, value);
		assertThat(assetWrapperRepository.findById(assetID).get().getNotes().size()).isEqualTo(
				originalCount);
	}

}
