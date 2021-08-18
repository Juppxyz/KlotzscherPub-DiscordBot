package xyz.jupp.discord.core;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.jupp.discord.commands.ActiveCommand;
import xyz.jupp.discord.commands.HelpCommand;
import xyz.jupp.discord.commands.NicknameResetCommand;
import xyz.jupp.discord.commands.handler.CommandHandler;
import xyz.jupp.discord.database.MongoDB;
import xyz.jupp.discord.events.NicknameChangeListener;
import xyz.jupp.discord.events.OnGuildJoinListener;
import xyz.jupp.discord.events.OnReadyListener;
import xyz.jupp.discord.events.RegularRoleListener;
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
            log.info(prefix + "build the bot ..");
            try {
                jda = JDABuilder.createDefault(SecretKey.key)
                        .enableIntents(GatewayIntent.GUILD_MEMBERS)
                        .addEventListeners(new OnReadyListener())
                        .addEventListeners(new CommandHandler())
                        .addEventListeners(new NicknameChangeListener())
                        .addEventListeners(new OnGuildJoinListener())
                        .addEventListeners(new RegularRoleListener())
                        .setActivity(Activity.playing("an der Bar")).build();

                System.out.println(jda.getEventManager().getRegisteredListeners());

                log.info(prefix + "register commands ..");
                CommandHandler.addCommand(new NicknameResetCommand());
                CommandHandler.addCommand(new ActiveCommand());
                CommandHandler.addCommand(new HelpCommand());

                log.info(prefix + "try to create connection with mongodb ..");
                MongoDB.getInstance();

                log.info(prefix + "bot started successfully.");
            } catch (LoginException e) {
                e.printStackTrace();
            }
        }
        return jda;
    }


    public static void main(String[] args) {
        log.info(prefix + "start the klotzscherpub bot .. ");
        getJda();
    }
    

    // shutdown the bot
    public static void shutdown(){
        log.warn("shutdown the klotzscherpub bot ..");
        getJda().shutdownNow();
    }

    // get the prefix from the bot
    public static String getPrefix() {
        return prefix;
    }

    // get the prefix for the chat commands
    public static String getChatPrefix() {
        return chatPrefix;
    }


}
