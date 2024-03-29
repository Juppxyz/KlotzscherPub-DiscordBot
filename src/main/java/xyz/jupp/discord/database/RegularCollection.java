package xyz.jupp.discord.database;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import net.dv8tion.jda.api.entities.Member;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.jetbrains.annotations.NotNull;
import xyz.jupp.discord.log.LoggerUtil;

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
        this.member = member;
    }


    public boolean existMemberInDatabase(){
        Bson searchFilter = eq("member_id", member.getId());
        FindIterable<Document> iterable = getMongoCollection().find(searchFilter);
        return iterable.first() != null;
    }


    public void updateDatetime(long activeTime, String effectiveName) {
        Bson searchFilter = eq("member_id", member.getId());
        Bson updatedDocument = new Document("active_time", activeTime).append("member_name", effectiveName);
        getMongoCollection().updateOne(searchFilter, new Document("$set", updatedDocument));
        log.log("updated active_time (" + activeTime + ")", member.getId());
    }


    // check no if user exist, because its only usage in RegularRoleListener
    public long getActiveTime() {
        Bson searchFiler = eq("member_id", member.getId());
        FindIterable<Document> iterable = getMongoCollection().find(searchFiler);
        Document document = iterable.first();
        if (document != null){
            for (Map.Entry<String, Object> entry : document.entrySet()){
                if (entry.getKey().equals("active_time")){
                    return Long.parseLong(String.valueOf(entry.getValue()));
                }
            }
        }

        return 0;
    }


    public String[] getMostActiveTimeUser() {
        Bson sortCondition = new Document("active_time", -1);
        FindIterable<Document> documentFindIterable = getMongoCollection().find().sort(sortCondition).limit(1);
        Document document = documentFindIterable.first();
        String[] result = {document.getString("member_name"), String.valueOf(document.getLong("active_time"))};
        return result;
    }


    public void createNewMemberInDatabase() {
        if (!existMemberInDatabase()){
            Document document = new Document("member_id", member.getId());
            document.append("member_name", member.getEffectiveName());
            document.append("active_time", 0L);
            getMongoCollection().insertOne(document);
            log.log("create new member in collection", member.getId());
        }else {
            log.warn("tried to create an existing member", member.getId());
        }

    }


    // Getter
    private MongoCollection<Document> getMongoCollection() {
        return mongoCollection;
    }
}
