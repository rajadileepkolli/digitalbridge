package com.digitalbridge.config;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoExceptionTranslator;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import com.digitalbridge.mongodb.audit.MongoAuditorProvider;
import com.digitalbridge.mongodb.convert.ObjectConverters;
import com.digitalbridge.mongodb.event.CascadeSaveMongoEventListener;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.connection.ClusterConnectionMode;

@Configuration(proxyBeanMethods = false)
public class MongoDBConfiguration extends AbstractMongoClientConfiguration {

	private static final String DATABASE = "digitalbridge";

	@Value("${mongo.primaryhost}")
	private String primaryhost;

	@Value("${mongo.secondaryhost}")
	private String secondaryhost;

	@Value("${mongo.teritoryhost}")
	private String teritoryhost;

	@Value("${mongo.replicasetname}")
	private String replicasetName;

	@Value("${mongo.superadminpassword}")
	private String superadminpassword;

	@Value("${mongo.primaryport}")
	private int primaryport;

	@Value("${mongo.secondaryport}")
	private int secondaryport;

	@Value("${mongo.teritoryport}")
	private int teritoryport;

	/** {@inheritDoc} */
	@Override
	protected String getDatabaseName() {
		return DATABASE;
	}

	@Override
	protected boolean autoIndexCreation() {
		return true;
	}


	/** {@inheritDoc} */
	@Override
	protected List<String> getMappingBasePackages() {
		return List.of("com.digitalbridge.domain");
	}


	@Bean
	@Profile(value = {"!prod"})
	@Override
	public MongoClient mongoClient() {
		return MongoClients.create(mongoClientSettings());
	}

	@Bean
	@Profile(value = {"prod"})
	public MongoClient prodMongoClient() {

		ServerAddress primary = new ServerAddress(
				new InetSocketAddress(primaryhost, primaryport));
		ServerAddress secondary = new ServerAddress(
				new InetSocketAddress(secondaryhost, secondaryport));
		ServerAddress tertiary = new ServerAddress(
				new InetSocketAddress(teritoryhost, teritoryport));
		List<ServerAddress> seeds = List.of(primary, secondary, tertiary);

		MongoCredential credential = MongoCredential.createCredential("digitalbridgeAdmin",
				DATABASE, superadminpassword.toCharArray());

		return MongoClients.create(
				MongoClientSettings.builder()
						.credential(credential)
						.applyToClusterSettings(builder ->
								builder.mode(ClusterConnectionMode.MULTIPLE).hosts(seeds)
										.requiredReplicaSetName(replicasetName))
						.build());
	}

	@Bean
	@Primary
	public MappingMongoConverter mongoConverter() {
		MongoMappingContext mappingContext = new MongoMappingContext();
		mappingContext.setAutoIndexCreation(true);
		DefaultDbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDbFactory());
		MappingMongoConverter mongoConverter = new MappingMongoConverter(dbRefResolver,
				mappingContext);
		mongoConverter.setTypeMapper(new DefaultMongoTypeMapper(null));
		mongoConverter.setCustomConversions(customConversions());
		return mongoConverter;
	}

	/** {@inheritDoc} */
	@Override
	@Bean
	public MongoCustomConversions customConversions() {
		List<Converter<?, ?>> converters = new ArrayList<>(ObjectConverters.getConvertersToRegister());
		return new MongoCustomConversions(converters);
	}

	@Bean
	public MongoExceptionTranslator exceptionTranslator() {
		return new MongoExceptionTranslator();
	}


	@Bean
	public AuditorAware<String> auditorProvider() {
		return new MongoAuditorProvider<String>();
	}


	@Bean
	public CascadeSaveMongoEventListener cascadingMongoEventListener() {
		return new CascadeSaveMongoEventListener();
	}

}
