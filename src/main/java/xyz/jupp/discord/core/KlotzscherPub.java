package xyz.jupp.discord.core;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import xyz.jupp.discord.commands.ActiveCommand;
import xyz.jupp.discord.commands.HelpCommand;
import xyz.jupp.discord.commands.TopCommand;
import xyz.jupp.discord.commands.handler.CommandHandler;
import xyz.jupp.discord.database.MongoDB;
import xyz.jupp.discord.events.*;
import xyz.jupp.discord.log.LoggerUtil;
import xyz.jupp.discord.utils.SecretKey;

import javax.security.auth.login.LoginException;
import java.util.EnumSet;

public class KlotzscherPub {

    private static final String prefix = "[KlotzscherPub-Bot] ";
    private static final String chatPrefix = "%";

    /** logger factory for the bot */
    private static LoggerUtil logger = new LoggerUtil(KlotzscherPub.class.getSimpleName());


    private final static EnumSet<GatewayIntent> gatewayIntents = EnumSet.of(GatewayIntent.DIRECT_MESSAGES, GatewayIntent.GUILD_EMOJIS,
            GatewayIntent.GUILD_VOICE_STATES, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_PRESENCES);

    /** JDA Builder for the bot */
    private static JDA jda;
    public static JDA getJda() {
        if (jda == null) {
            logger.log( "build the bot ..");
            try {
                jda = JDABuilder.createDefault(SecretKey.key)
                        .disableCache(CacheFlag.ACTIVITY)
                        .setMemberCachePolicy(MemberCachePolicy.ALL)
                        .setEnabledIntents(GatewayIntent.GUILD_MEMBERS)
                        .enableIntents(gatewayIntents)
                        .setAutoReconnect(true)
                        .addEventListeners(new OnReadyListener(), new CommandHandler(),
                                           new NicknameChangeListener(), new OnGuildJoinListener(),
                                           new RegularRoleListener(), new RoleChangeListener())
                        .setActivity(Activity.playing("an der Bar")).build();

                logger.log("register commands ..");

                //CommandHandler.addCommand(new DebugCommand());
                CommandHandler.addCommand(new ActiveCommand());
                CommandHandler.addCommand(new HelpCommand());
                CommandHandler.addCommand(new TopCommand());

                logger.log("try to create connection with mongodb ..");
                MongoDB.getInstance();

                logger.log("bot started successfully.");
            } catch (LoginException e) {
                e.printStackTrace();
            }
        }
        return jda;
    }


    public static void main(String[] args) {
        logger.log( "start the klotzscherpub bot .. ");
        getJda();
    }
    

    // shutdown the bot
    public static void shutdown(){
        logger.warn("shutdown the klotzscherpub bot ..");
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
