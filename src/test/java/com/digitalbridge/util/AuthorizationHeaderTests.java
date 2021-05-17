package com.digitalbridge.util;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthorizationHeaderTests {

	private final AuthorizationHeader authorizationHeader = new AuthorizationHeader();

	@Test
	public final void testGetHeader() {
		String val = authorizationHeader.getBasicHeader("appUser", "appPassword");
		assertThat(val).isNotNull();
	}

}
