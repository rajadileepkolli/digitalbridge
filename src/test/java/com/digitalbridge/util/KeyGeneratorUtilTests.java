package com.digitalbridge.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.digitalbridge.exception.DigitalBridgeException;

public class KeyGeneratorUtilTests {

	@Rule
	public TemporaryFolder folder = new TemporaryFolder();

	@Test
	public final void testwritetoFile() throws IOException {
		File createdFile = folder.newFile("myfile.txt");
		KeyGeneratorUtil.writetoFile(createdFile);
		assertTrue(createdFile.exists());
	}

	@Test
	public final void testencrypt() throws DigitalBridgeException {
		String encryptedValue = KeyGeneratorUtil.encrypt("RAJA");
		assertNotNull(encryptedValue);
		assertEquals(encryptedValue, "YztT5rQoZfij+yfS9fOHbw==");
	}

}
