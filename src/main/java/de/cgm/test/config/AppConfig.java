package de.cgm.test.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class AppConfig {

    @Autowired
    private MongoDbConfigurationProperties mongoDbConfigurationProperties;
    /*
     * Use the standard Mongo driver API to create a com.mongodb.client.MongoClient instance.
     */
    private MongoClient mongoClient() {
        return MongoClients.create(mongoDbConfigurationProperties.getConnectionString());
    }

    public @Bean MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), mongoDbConfigurationProperties.getDatabase());
    }
}
