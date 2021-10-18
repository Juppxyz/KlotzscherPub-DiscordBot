package xyz.jupp.discord.database;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import net.dv8tion.jda.api.entities.Member;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.jetbrains.annotations.NotNull;
import xyz.jupp.discord.log.LoggerUtil;

import java.util.HashMap;
import java.util.Map;

import static com.mongodb.client.model.Filters.eq;

public class RegularCollection {
    private RegularCollection () {}

    // logger
    private final static LoggerUtil log = new LoggerUtil(RegularCollection.class.getSimpleName());

    private Member member;
    private final MongoCollection<Document> mongoCollection = MongoDB.getInstance().getDatabase().getCollection("stammkunden");


    // constructor
    public RegularCollection(@NotNull Member member){
        MongoDB.getInstance();
        this.member = member;
    }


    public boolean existMemberInDatabase(){
        Bson searchFilter = eq("member_id", member.getId());
        FindIterable<Document> iterable = getMongoCollection().find(searchFilter);
        return iterable.cursor().hasNext();
    }


    public void updateDatetime(long activeTime) {
        if (existMemberInDatabase()) {
            Bson searchFilter = eq("member_id", member.getId());
            Bson updatedDocument = new Document("active_time", activeTime);

            getMongoCollection().updateOne(searchFilter, new Document("$set", updatedDocument));
            log.log("updated active_time", member.getId());
        }

    }

    // check no if user exist, because its only usage in RegularRoleListener
    public long getActiveTime() {
        Bson searchFiler = eq("member_id", member.getId());
        FindIterable<Document> iterable = getMongoCollection().find(searchFiler);
        Document document = iterable.cursor().next();
        for (Map.Entry<String, Object> entry : document.entrySet()){
            if (entry.getKey().equals("active_time")){
                return Long.parseLong(String.valueOf(entry.getValue()));
            }

        }

        return 0;
    }


    // get all user and the active time  from the database
    public HashMap<String, Long> getAllActiveTimesFromUsers() {
        FindIterable<Document> iterable = getMongoCollection().find();
        HashMap<String, Long> userActiveTimesMap = new HashMap<>();
        for (Document document : iterable){
            String username = document.getString("member_name");
            long activeTime = document.getLong("active_time");
            userActiveTimesMap.put(username, activeTime);
        }

        return userActiveTimesMap;
    }



    public void createNewMemberInDatabase() {
        if (!existMemberInDatabase()){
            Document document = new Document("member_id", member.getId());
            document.append("member_name",member.getEffectiveName());
            document.append("active_time", 0L);
            getMongoCollection().insertOne(document);
            log.log("create new member in collection.", member.getEffectiveName());
        }else {
            log.warn("tried to create an existing member.", "v0.0.1");
        }

    }


    // Getter
    private MongoCollection<Document> getMongoCollection() {
        return mongoCollection;
    }
}
