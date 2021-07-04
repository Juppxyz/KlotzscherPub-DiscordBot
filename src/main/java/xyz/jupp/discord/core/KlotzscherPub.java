package xyz.jupp.discord.core;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.jupp.discord.commands.handler.CommandHandler;
import xyz.jupp.discord.utils.SecretKey;

import javax.security.auth.login.LoginException;

public class KlotzscherPub {

    private static final String prefix = "[KlotzscherPub-Bot] ";
    private static final String chatPrefix = "%";

    /** logger factory for the bot */
    private static Logger log = LoggerFactory.getLogger(KlotzscherPub.class);

    /** JDA Builder for the bot */
    private static JDA jda;
    public static JDA getJda() {
        if (jda == null) {
            try {
                jda = JDABuilder.createLight(SecretKey.key).build();
                log.info("verify was successful.");
            } catch (LoginException e) {
                e.printStackTrace();
            }
        }
        return jda;
    }


    public static void main(String[] args) {
        log.info("start the klotzscherpub bot .. ");
        getJda();

        log.info("register commands ...");


    }


    private void registerCommands() {
    }


    /** shutdown the bot */
    public static void shutdown(){
        log.warn("shutdown the klotzscherpub bot ..");
        getJda().shutdownNow();
    }


    /** get the prefix from the bot */
    public static String getPrefix() {
        return prefix;
    }

    /** get the prefix for the chat commands */
    public static String getChatPrefix() {
        return chatPrefix;
    }
}
