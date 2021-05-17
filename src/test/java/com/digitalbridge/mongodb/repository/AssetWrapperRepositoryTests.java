package com.digitalbridge.mongodb.repository;

import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;

import com.digitalbridge.DigitalBridgeApplicationTests;
import com.digitalbridge.domain.AssetWrapper;
import com.digitalbridge.security.SecurityUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AssetWrapperRepositoryTests extends DigitalBridgeApplicationTests {

	@Test
	public final void testUpdate() {
		SecurityUtils.runAs(USERNAME, PASSWORD, ROLE_USER);
		AssetWrapper assetWrapper = assetWrapperRepository.findById(assetID).orElse(new AssetWrapper());
		Long initialVersion = assetWrapper.getVersion();
		String originalName = assetWrapper.getAssetName();
		assetWrapper.setAssetName("Customized Asset");
		int notesCount = assetWrapper.getNotes().size();
		assetWrapperRepository.save(assetWrapper);
		AssetWrapper updatedAssetWrapper = assetWrapperRepository.findById(assetID).orElse(new AssetWrapper());
		assertThat(updatedAssetWrapper.getVersion() == initialVersion + 1).isTrue();
		assertThat(originalName).isNotEqualTo(updatedAssetWrapper.getAssetName());
		assertThat(notesCount).isEqualTo(updatedAssetWrapper.getNotes().size());
		assertThat(updatedAssetWrapper.getLastModifiedBy()).isEqualTo(USERNAME);
	}

	@Test
	public final void testUpdateFail() {
		SecurityUtils.runAs(USERNAME, PASSWORD, ROLE_USER);
		AssetWrapper assetWrapper = assetWrapperRepository.findById(assetID).get();
		assetWrapper.setAssetName("Customized Asset");
		SecurityContextHolder.clearContext();
		assertThatThrownBy(() -> assetWrapperRepository.save(assetWrapper))
				.isInstanceOf(AuthenticationCredentialsNotFoundException.class);
	}

}
