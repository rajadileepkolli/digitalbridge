package com.digitalbridge;

import com.digitalbridge.mongodb.repository.AddressRepository;
import com.digitalbridge.mongodb.repository.AssetWrapperRepository;
import com.digitalbridge.mongodb.repository.NotesRepository;
import com.digitalbridge.mongodb.repository.UserRepository;
import com.digitalbridge.service.AddressService;
import com.digitalbridge.service.AssetWrapperService;
import com.mongodb.client.MongoClient;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;

@SpringBootTest(classes = DigitalBridgeApplication.class)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@WebAppConfiguration
@ActiveProfiles("local")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class DigitalBridgeApplicationTests {

	protected static final String assetID = "56094694bd51636546272ee8";
	protected static final String USERNAME = "JUNIT_TEST";
	protected static final String PASSWORD = "JUNIT_PASSWORD";
	protected static final String ROLE_USER = "ROLE_USER";
	protected static final String ROLE_ADMIN = "ROLE_ADMIN";


	@Autowired
	protected AssetWrapperService assetWrapperService;
	@Autowired
	protected AddressService addressService;

	@Autowired
	protected AssetWrapperRepository assetWrapperRepository;
	@Autowired
	protected NotesRepository notesRepository;
	@Autowired
	protected AddressRepository addressRepository;
	@Autowired
	protected UserRepository userRepository;

	@Autowired
	protected MongoClient mongoClient;

	@Autowired
	private WebApplicationContext context;

	protected MockMvc mockMvc;

	protected Pageable pageable = PageRequest.of(0, 10);

	@BeforeAll
	public void setUp(RestDocumentationContextProvider restDocumentation) {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
				.apply(documentationConfiguration(restDocumentation))
				.alwaysDo(document("{method-name}/{step}/",
						preprocessRequest(prettyPrint()),
						preprocessResponse(prettyPrint())))
				.apply(SecurityMockMvcConfigurers.springSecurity()).build();
	}

	@AfterAll
	public void tearDown() throws Exception {
		SecurityContextHolder.clearContext();
	}

}
