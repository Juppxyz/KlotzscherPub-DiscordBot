package xyz.jupp.discord.log;

import org.bson.Document;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.jupp.discord.database.MongoDB;
import xyz.jupp.discord.utils.TimeUtil;

public class LoggerUtil {
    private LoggerUtil() {}


    // knowledge variables
    private Logger logger;


    public LoggerUtil(@NotNull String className) {
        this.logger = LoggerFactory.getLogger(className);
    }


    // writes the log in the console
    public void log(@NotNull String content){
        logger.info(content);
    }


    public void warn(@NotNull String content){
        logger.warn(content);
    }


    public void error(@NotNull String content, @Nullable Exception exception){
        if (exception != null){
            exception.printStackTrace();
        }
        logger.warn("EXCEPTION " + content);
    }



    public void log(@NotNull String content, @NotNull String info){
        Document document = createMongoDocument(content, info, "INFO");
        insertLogToMongoDB(document);
    }


    public void warn(@NotNull String content, @NotNull String info){
        Document document = createMongoDocument(content, info, "WARN");
        insertLogToMongoDB(document);
    }


    private Document createMongoDocument(@NotNull String content, @NotNull String info, @NotNull String type){
        Document document = new Document();
        document.append("datetime", TimeUtil.getDateTime());
        document.append("content", content);
        document.append("type", type);
        document.append("info", info);
        return document;
    }


    private void insertLogToMongoDB(@NotNull Document document){
        new Thread(() -> MongoDB.getInstance().insertLog(document)).start();
    }


}
