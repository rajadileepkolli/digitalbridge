package com.digitalbridge.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.digitalbridge.util.Constants;
import com.google.gson.GsonBuilder;

import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.client.http.JestHttpClient;

@Configuration(proxyBeanMethods = false)
public class ElasticSearchConfiguration {

	private final Environment env;

	public ElasticSearchConfiguration(Environment env) {
		this.env = env;
	}

	@Bean
	public JestHttpClient jestClient() {
		// Construct a new Jest client according to configuration via factory
		JestClientFactory factory = new JestClientFactory();
		HttpClientConfig httpClientConfig = new HttpClientConfig.Builder(
				getConnectionURL()).multiThreaded(true).connTimeout(Constants.CONNTIMEOUT)
						.readTimeout(Constants.READTIMEOUT)
						.maxTotalConnection(Constants.MAXTOTALCONNECTION)
						// .requestCompressionEnabled(true)
						.gson(new GsonBuilder().create()).build();
		factory.setHttpClientConfig(httpClientConfig);
		JestHttpClient jestHttpClient = (JestHttpClient) factory.getObject();
		CloseableHttpClient closableHttpClient = HttpClientBuilder.create().build();
		jestHttpClient.setHttpClient(closableHttpClient);
		return jestHttpClient;
	}


	public List<String> localConnectionURL() {
		return Collections.singletonList("http://127.0.0.1:9200");
	}

	public List<String> iLabConnectionURL() {
		return Collections.singletonList("http://xx.xx.xx.xx:9200");
	}

	public List<String> getConnectionURL() {
		if (findProfile("iLab")) {
			return iLabConnectionURL();
		}
		else {
			return localConnectionURL();
		}
	}

	private boolean findProfile(String profileName) {
		return Arrays.asList(env.getActiveProfiles()).contains(profileName);
	}

}
