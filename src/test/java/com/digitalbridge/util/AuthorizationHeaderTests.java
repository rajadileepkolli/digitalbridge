package com.digitalbridge.util;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class AuthorizationHeaderTests {
  
  private AuthorizationHeader authorizationHeader = new AuthorizationHeader();

  @Test
  public final void testGetHeader() {
    String val = authorizationHeader.getBasicHeader("appUser", "appPassword");
    assertNotNull(val);
  }

}
