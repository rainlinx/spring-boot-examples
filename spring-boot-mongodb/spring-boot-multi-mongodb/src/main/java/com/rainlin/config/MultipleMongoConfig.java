package com.rainlin.config;

import com.mongodb.client.MongoClients;
import com.rainlin.config.props.MultipleMongoProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

/**
 * MongoDb多数据源配置
 *
 * @author rainlin
 */
@Configuration
public class MultipleMongoConfig {

    private final MultipleMongoProperties mongoProperties;

    public MultipleMongoConfig(MultipleMongoProperties mongoProperties) {
        this.mongoProperties = mongoProperties;
    }

    @Primary
    @Bean(name = "primaryMongoTemplate")
    public MongoTemplate primaryMongoTemplate() {
        return new MongoTemplate(primaryFactory());
    }

    @Bean
    @Qualifier("secondaryMongoTemplate")
    public MongoTemplate secondaryMongoTemplate() {
        return new MongoTemplate(secondaryFactory());
    }

    @Bean
    @Primary
    public MongoDatabaseFactory primaryFactory() {
        return new SimpleMongoClientDatabaseFactory(MongoClients.create(mongoProperties.getPrimary().getUri()), mongoProperties.getPrimary().getDatabase());
    }

    @Bean
    public MongoDatabaseFactory secondaryFactory() {
        return new SimpleMongoClientDatabaseFactory(MongoClients.create(mongoProperties.getSecondary().getUri()), mongoProperties.getSecondary().getDatabase());
    }
}