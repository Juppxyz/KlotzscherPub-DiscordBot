package xyz.jupp.discord.database;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;
import xyz.jupp.discord.log.LoggerUtil;
import xyz.jupp.discord.utils.SecretKey;

import java.util.concurrent.TimeUnit;

public class MongoDB {
    private MongoDB() {}

    // logger
    private static final LoggerUtil logger = new LoggerUtil(MongoDB.class.getSimpleName());

    private static MongoDB instance = null;
    public synchronized static MongoDB getInstance() {
        if (instance == null) {
            instance = new MongoDB();
            logger.log("connected to database");
        }
        return instance;
    }

    private static MongoClient client;
    private static MongoDatabase database;
    private static MongoClientSettings settings;

    static {
        try {
            settings = MongoClientSettings.builder()
                    .applyConnectionString(new ConnectionString(SecretKey.connectionString))
                    .applyToSocketSettings(builder ->
                            builder.readTimeout(30, TimeUnit.SECONDS)
                                    .connectTimeout(10, TimeUnit.SECONDS)
                    ).build();

            client = MongoClients.create(settings);
            database = client.getDatabase("discord");
            database.runCommand(new Document("ping", 1));
            logger.log("mongodb ping ok");
        } catch (Exception e) {
            throw e;
        }
    }

    public void insertLog(@NotNull Document document) {
        getDatabase().getCollection("logs").insertOne(document);
    }

    MongoDatabase getDatabase() {
        return database;
    }

}
