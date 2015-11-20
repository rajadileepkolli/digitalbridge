package com.digitalbridge;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class DigitalBridgeApplicationMVCTests extends DigitalBridgeApplicationTests {

	@Test
	public void test() throws Exception {
		this.mockMvc
				.perform(RestDocumentationRequestBuilders.get("/api")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcRestDocumentation.document("index"));
	}

	@Test
	public void geoResultsTestMVC() throws Exception {
		this.mockMvc
				.perform(RestDocumentationRequestBuilders
						.get("/api/address/search/findByLocationNear?point=40.7408231,-74.0014541&distance=1.0MILES&page=0&size=10")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content()
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(MockMvcRestDocumentation.document("geoResults"));
	}
}
