package com.digitalbridge.util;

import java.util.Base64;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/digitalbridge/search")
@RestController
public class AuthorizationHeader {

	/**
	 * <p>
	 * getBasicHeader.
	 * </p>
	 *
	 * @param userName a {@link java.lang.String} object.
	 * @param password a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 */
	@RequestMapping(value = "/getEncoded/{username}/{password}")
	public String getBasicHeader(@PathVariable("username") String userName,
			@PathVariable("password") String password) {
		String appendString = userName + ":" +
				password;
		return "Basic " + Base64.getEncoder().encodeToString(appendString.getBytes());
	}

}
