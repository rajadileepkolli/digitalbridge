package com.digitalbridge.controller;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

import com.digitalbridge.DigitalBridgeApplicationTests;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class DigitalBridgeApplicationMVCTests extends DigitalBridgeApplicationTests {

	@Test
	public void test() throws Exception {
		this.mockMvc
		.perform(RestDocumentationRequestBuilders.get("/api")
				.header("Authorization", "Basic YXBwVXNlcjphcHBQYXNzd29yZA==")
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(document("index"))
		.andDo(document("headers",requestHeaders( 
                headerWithName("Authorization").description(
                        "Basic auth credentials"))));
}
}
