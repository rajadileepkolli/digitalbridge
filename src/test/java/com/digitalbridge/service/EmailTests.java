package com.digitalbridge.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.digitalbridge.DigitalBridgeApplicationTests;

public class EmailTests extends DigitalBridgeApplicationTests {

  @Autowired Email email;

  @Test
  public final void testSendEmail() {
    email.sendEmail("rajakolli@deloitte.com","Mail Sent Successfully !!");
  }

}
