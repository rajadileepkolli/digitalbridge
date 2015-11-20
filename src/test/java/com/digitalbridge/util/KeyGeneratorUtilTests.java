package com.digitalbridge.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

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
	public final void testencrypt() throws InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		String encryptedValue = KeyGeneratorUtil.encrypt("RAJA");
		assertNotNull(encryptedValue);
		assertEquals(encryptedValue, "YztT5rQoZfij+yfS9fOHbw==");
	}

}
