package com.digitalbridge.config;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.connection.ClusterConnectionMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoExceptionTranslator;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.convert.CustomConversions;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import com.digitalbridge.mongodb.audit.MongoAuditorProvider;
import com.digitalbridge.mongodb.convert.ObjectConverters;
import com.digitalbridge.mongodb.event.CascadeSaveMongoEventListener;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

/**
 * <p>
 * MongoDBConfiguration class.
 * </p>
 *
 * @author rajakolli
 * @version 1:0
 */
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
	public MongoClient mongoClient() {

		ServerAddress primary = new ServerAddress(
				new InetSocketAddress(primaryhost, primaryport));
		ServerAddress secondary = new ServerAddress(
				new InetSocketAddress(secondaryhost, secondaryport));
		ServerAddress teritory = new ServerAddress(
				new InetSocketAddress(teritoryhost, teritoryport));
		List<ServerAddress> seeds = List.of(primary, secondary, teritory);

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
	public MongoDatabaseFactory mongoDbFactory() {
		return new SimpleMongoClientDatabaseFactory(mongoClient(), getDatabaseName());
	}

	/**
	 * <p>
	 * mongoTemplate.
	 * </p>
	 *
	 * @return a {@link org.springframework.data.mongodb.core.MongoTemplate} object.
	 */
	@Bean
	public MongoTemplate mongoTemplate() {
		return new MongoTemplate(mongoDbFactory(), mongoConverter());
	}

	/**
	 * <p>
	 * mongoConverter.
	 * </p>
	 *
	 * @return a
	 * {@link org.springframework.data.mongodb.core.convert.MappingMongoConverter} object.
	 */
	@Bean
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
	public CustomConversions customConversions() {
		List<Converter<?, ?>> converters = new ArrayList<Converter<?, ?>>();
		converters.addAll(ObjectConverters.getConvertersToRegister());
		return new CustomConversions(converters);
	}

	/**
	 * <p>
	 * exceptionTranslator.
	 * </p>
	 *
	 * @return a {@link org.springframework.data.mongodb.core.MongoExceptionTranslator}
	 * object.
	 */
	@Bean
	public MongoExceptionTranslator exceptionTranslator() {
		return new MongoExceptionTranslator();
	}

	/**
	 * <p>
	 * auditorProvider.
	 * </p>
	 *
	 * @return a {@link org.springframework.data.domain.AuditorAware} object.
	 */
	@Bean
	public AuditorAware<String> auditorProvider() {
		return new MongoAuditorProvider<String>();
	}

	/**
	 * <p>
	 * cascadingMongoEventListener.
	 * </p>
	 *
	 * @return a {@link com.digitalbridge.mongodb.event.CascadeSaveMongoEventListener}
	 * object.
	 */
	@Bean
	public CascadeSaveMongoEventListener cascadingMongoEventListener() {
		return new CascadeSaveMongoEventListener();
	}

}
