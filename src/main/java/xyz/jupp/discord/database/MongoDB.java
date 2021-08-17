package xyz.jupp.discord.database;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.jupp.discord.core.KlotzscherPub;
import xyz.jupp.discord.utils.SecretKey;


public class MongoDB {
    private MongoDB() {}

    /* This class is for the access on the mongodb database.
     * The MongoClient will be connect to the database server.*/

    // logger
    private final static Logger log = LoggerFactory.getLogger(MongoDB.class);

    // single pattern
    private static MongoDB instance = null;
    public synchronized static MongoDB getInstance() {
        if (instance == null){
            log.info(KlotzscherPub.getChatPrefix() + "connected to database.");
            instance = new MongoDB();
        }
        return instance;
    }


    // mongodb client settings
    private final static ConnectionString mongoURI = new ConnectionString(SecretKey.connectionString);
    private final static MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(mongoURI).retryWrites(true).build();


    // variable for the mongoclient
    private final com.mongodb.client.MongoClient mongoClient = MongoClients.create(settings);


    private final MongoDatabase database = getMongoClient().getDatabase("discord");
    MongoDatabase getDatabase() {
        return database;
    }

    // Getter
    private MongoClient getMongoClient() {
        return mongoClient;
    }
}
