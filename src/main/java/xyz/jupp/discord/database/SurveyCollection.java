package xyz.jupp.discord.database;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.jetbrains.annotations.NotNull;
import xyz.jupp.discord.log.LoggerUtil;

import java.util.Date;

import static com.mongodb.client.model.Filters.eq;

public class SurveyCollection {
    private SurveyCollection() {}

    // logger
    private final static LoggerUtil log = new LoggerUtil(RegularCollection.class.getSimpleName());

    private final MongoCollection<Document> mongoCollection = MongoDB.getInstance().getDatabase().getCollection("surveys");

    private String messageID;

    public SurveyCollection(@NotNull String messageID) {
        this.messageID = messageID;
    }

    public boolean isMessageASurvey(){
        Bson searchFilter = eq("survey_id", messageID);
        FindIterable<Document> iterable = getMongoCollection().find(searchFilter);
        System.out.println(iterable);
        return iterable.first() != null;
    }

    public boolean createNewSurvey(){
        Document newDocument = new Document("survey_id", messageID);
        newDocument.append("datetime", new Date());
        getMongoCollection().insertOne(newDocument);
        log.log("create new survey", messageID);
        return true;
    }


    // Getter
    public MongoCollection<Document> getMongoCollection() {
        return mongoCollection;
    }
}
