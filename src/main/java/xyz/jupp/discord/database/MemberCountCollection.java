package xyz.jupp.discord.database;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import xyz.jupp.discord.log.LoggerUtil;

import java.util.Date;

public class MemberCountCollection {
    public MemberCountCollection () {}

    // logger
    private final static LoggerUtil log = new LoggerUtil(MemberCountCollection.class.getSimpleName());

    private final MongoCollection<Document> mongoCollection = MongoDB.getInstance().getDatabase().getCollection("memberCount");

    public void saveGuildMemberCount(int amount) {
        Document document = new Document("datetime", new Date());
        document.append("memberCount", amount);
        this.mongoCollection.updateOne(new Document("guild", "klotzscherpub"), new Document("$set", document));
        log.log("saved member count", String.valueOf(amount));
    }


}
