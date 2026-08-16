package pe.edu.vallegrande.app.config;

import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.SimpleReactiveMongoDatabaseFactory;

@Configuration
public class LocalMongoConfig {

    @Value("${mongodb.local.uri}")
    private String localUri;

    @Value("${mongodb.local.database}")
    private String localDatabase;

    @Bean("localMongoDatabaseFactory")
    public ReactiveMongoDatabaseFactory localMongoDatabaseFactory() {
        return new SimpleReactiveMongoDatabaseFactory(
                MongoClients.create(localUri), localDatabase);
    }

    @Bean("localMongoTemplate")
    public ReactiveMongoTemplate localMongoTemplate() {
        return new ReactiveMongoTemplate(localMongoDatabaseFactory());
    }
}
