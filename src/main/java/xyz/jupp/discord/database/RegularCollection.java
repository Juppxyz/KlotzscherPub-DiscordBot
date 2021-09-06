package xyz.jupp.discord.database;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import net.dv8tion.jda.api.entities.Member;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.jupp.discord.core.KlotzscherPub;
import xyz.jupp.discord.utils.TimeUtil;

import java.util.HashMap;

import static com.mongodb.client.model.Filters.eq;

public class RegularCollection {
    private RegularCollection () {}

    // logger
    private final static Logger log = LoggerFactory.getLogger(RegularCollection.class);

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
            log.info(KlotzscherPub.getPrefix() + "updated active_time for " + member.getId() + ". (" + TimeUtil.getDateTime() + ")");
        }

    }

    // check no if user exist, because its only usage in RegularRoleListener
    public long getActiveTime() {
        Bson searchFiler = eq("member_id", member.getId());
        FindIterable<Document> iterable = getMongoCollection().find(searchFiler);
        if (iterable.cursor().hasNext()){
            return iterable.cursor().next().getLong("active_time");
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
            log.info(KlotzscherPub.getPrefix() + "create new member (" + member.getEffectiveName() + ") in database. " + " (" + TimeUtil.getDateTime() + ")");
        }else {
            log.info(KlotzscherPub.getPrefix() + "tried to create an existing member. (0.1.1)");
        }

    }


    // Getter
    private MongoCollection<Document> getMongoCollection() {
        return mongoCollection;
    }
}
