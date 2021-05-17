package com.digitalbridge.util;


import com.digitalbridge.exception.DigitalBridgeException;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class KeyGeneratorUtilTests {


	@Test
	public final void testWriteToFile() throws IOException {
		File createdFile = File.createTempFile("myfile", "txt");
		KeyGeneratorUtil.writetoFile(createdFile);
		assertThat(createdFile.exists()).isTrue();
	}

	@Test
	public final void testencrypt() throws DigitalBridgeException {
		String encryptedValue = KeyGeneratorUtil.encrypt("RAJA");
		assertThat(encryptedValue).isNotNull();
		assertThat(encryptedValue).isEqualTo("YztT5rQoZfij+yfS9fOHbw==");
	}

}
