package com.digitalbridge.mongodb.repository;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.digitalbridge.DigitalBridgeApplicationTests;
import com.digitalbridge.domain.AssetWrapper;
import com.digitalbridge.security.SecurityUtils;

public class AssetWrapperRepositoryTests extends DigitalBridgeApplicationTests {

	@Test
	public final void testUpdate() {
		AssetWrapper assetWrapper = assetWrapperRepository.findOne(assetID);
		Long initialVersion = assetWrapper.getVersion();
		String originalName = assetWrapper.getAssetName();
		assetWrapper.setAssetName("Customized Asset");
		int notesCount = assetWrapper.getNotes().size();
		SecurityUtils.runAs(USERNAME, PASSWORD, ROLE_USER);
		assetWrapperRepository.save(assetWrapper);
		AssetWrapper updatedAssetWrapper = assetWrapperRepository.findOne(assetID);
		assertTrue(updatedAssetWrapper.getVersion() == initialVersion + 1);
		assertTrue(originalName != updatedAssetWrapper.getAssetName());
		assertTrue(notesCount == updatedAssetWrapper.getNotes().size());
		assertTrue(updatedAssetWrapper.getLastModifiedBy().equals(USERNAME));
	}

	@Test(expected = NullPointerException.class)
	public final void testUpdateFail() {
		AssetWrapper assetWrapper = assetWrapperRepository.findOne(assetID);
		assetWrapper.setAssetName("Customized Asset");
		assetWrapperRepository.save(assetWrapper);
	}

}
