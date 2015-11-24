package com.digitalbridge.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.crypto.keygen.StringKeyGenerator;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

import com.digitalbridge.exception.DigitalBridgeException;
import com.digitalbridge.exception.DigitalBridgeExceptionBean;

/**
 * <p>
 * KeyGeneratorUtil class.
 * </p>
 *
 * @author rajakolli
 * @version 1 : 0
 */
public class KeyGeneratorUtil {

	// Authenticate and Authorize Mongodb
	// https://github.com/ihr/spring-boot-mongodb
	// http://ryanjbaxter.com/2015/01/06/securing-rest-apis-with-spring-boot/
	private static final Logger LOGGER = LoggerFactory.getLogger(KeyGeneratorUtil.class);

	private static final String KEY_GENERATOR = "ba189daec8f83047";

	/**
	 * <p>
	 * Constructor for KeyGeneratorUtil.
	 * </p>
	 */
	private KeyGeneratorUtil() {

	}

	static void writetoFile(File file) {
		FileOutputStream output = null;
		try {
			output = new FileOutputStream(file);
			StringKeyGenerator generator = KeyGenerators.string();
			// SHA-256 Algorithm
			StandardPasswordEncoder encoder = new StandardPasswordEncoder(
					generator.generateKey());
			String result = encoder.encode("myPassword");
			output.write(result.getBytes());
			output.close();
		}
		catch (IOException e1) {
			LOGGER.error("IOException :{}", e1.getMessage(), e1);
		}
	}

	/**
	 * <p>
	 * encrypt.
	 * </p>
	 *
	 * @param plainText a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 * @throws DigitalBridgeException if any
	 */
	public static String encrypt(String plainText) throws DigitalBridgeException {
		Key aesKey = new SecretKeySpec(KEY_GENERATOR.getBytes(), "AES");
		Cipher cipher;
		byte[] encoder = null;
		try {
			cipher = Cipher.getInstance("AES");
			byte[] plainTextByte = plainText.getBytes();
			cipher.init(Cipher.ENCRYPT_MODE, aesKey);
			byte[] encryptedByte = cipher.doFinal(plainTextByte);
			encoder = Base64.encodeBase64(encryptedByte);
		}
		catch (NoSuchAlgorithmException e) {
			LOGGER.error("Exception ::{}", e.getMessage(), e);
			DigitalBridgeExceptionBean bean = new DigitalBridgeExceptionBean();
			bean.setFaultCode("1102");
			bean.setFaultString("NoSuchAlgorithmException");
			throw new DigitalBridgeException(bean);
		}
		catch (NoSuchPaddingException e) {
			LOGGER.error("Exception ::{}", e.getMessage(), e);
			DigitalBridgeExceptionBean bean = new DigitalBridgeExceptionBean();
			bean.setFaultCode("1103");
			bean.setFaultString("NoSuchPaddingException");
			throw new DigitalBridgeException(bean);
		}
		catch (InvalidKeyException e) {
			LOGGER.error("Exception ::{}", e.getMessage(), e);
			DigitalBridgeExceptionBean bean = new DigitalBridgeExceptionBean();
			bean.setFaultCode("1103");
			bean.setFaultString("InvalidKeyException");
			throw new DigitalBridgeException(bean);
		}
		catch (IllegalBlockSizeException e) {
			LOGGER.error("Exception ::{}", e.getMessage(), e);
			DigitalBridgeExceptionBean bean = new DigitalBridgeExceptionBean();
			bean.setFaultCode("1105");
			bean.setFaultString("IllegalBlockSizeException");
			throw new DigitalBridgeException(bean);
		}
		catch (BadPaddingException e) {
			LOGGER.error("Exception ::{}", e.getMessage(), e);
			DigitalBridgeExceptionBean bean = new DigitalBridgeExceptionBean();
			bean.setFaultCode("1106");
			bean.setFaultString("BadPaddingException");
			throw new DigitalBridgeException(bean);
		}
		return new String(encoder);
	}

}
