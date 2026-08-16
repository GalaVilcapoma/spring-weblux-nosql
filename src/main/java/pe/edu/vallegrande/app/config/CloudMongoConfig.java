package pe.edu.vallegrande.app.config;

import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.SimpleReactiveMongoDatabaseFactory;

@Configuration
public class CloudMongoConfig {

    @Value("${spring.data.mongodb.uri}")
    private String cloudUri;

    @Value("${spring.data.mongodb.database}")
    private String cloudDatabase;

    @Primary
    @Bean("cloudMongoDatabaseFactory")
    public ReactiveMongoDatabaseFactory cloudMongoDatabaseFactory() {
        return new SimpleReactiveMongoDatabaseFactory(
                MongoClients.create(cloudUri), cloudDatabase);
    }

    @Primary
    @Bean("reactiveMongoTemplate")
    public ReactiveMongoTemplate reactiveMongoTemplate() {
        return new ReactiveMongoTemplate(cloudMongoDatabaseFactory());
    }
}
