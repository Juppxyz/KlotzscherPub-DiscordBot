package xyz.jupp.discord.core;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import xyz.jupp.discord.commands.handler.CommandHandler;
import xyz.jupp.discord.database.MongoDB;
import xyz.jupp.discord.events.*;
import xyz.jupp.discord.log.LoggerUtil;
import xyz.jupp.discord.tasks.AfkHandler;
import xyz.jupp.discord.utils.SecretKey;

import java.util.EnumSet;

public class KlotzscherPub {

    private static final String prefix = "[KlotzscherPub-Bot] ";

    // 300 Stunden
    private static final long neededTimeForRegularRole = 1080000000L;

    /** logger factory for the bot */
    private static final LoggerUtil logger = new LoggerUtil(KlotzscherPub.class.getSimpleName());

    private final static EnumSet<GatewayIntent> gatewayIntents = EnumSet.of(GatewayIntent.DIRECT_MESSAGES, GatewayIntent.GUILD_EMOJIS_AND_STICKERS,
            GatewayIntent.GUILD_VOICE_STATES, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES,
            GatewayIntent.GUILD_PRESENCES, GatewayIntent.SCHEDULED_EVENTS, GatewayIntent.MESSAGE_CONTENT);


    /** JDA Builder for the bot */
    private static JDA jda;
    public static JDA getJda() {
        if (jda == null) {
            logger.log( "build the bot ..");

            jda = JDABuilder.createDefault(SecretKey.key)
                    .setActivity(Activity.playing("eine runde Dart"))
                    .setEnabledIntents(GatewayIntent.GUILD_MEMBERS)
                    .setMemberCachePolicy(MemberCachePolicy.ALL)
                    .disableCache(CacheFlag.ACTIVITY)
                    .enableIntents(gatewayIntents)
                    .setAutoReconnect(true)
                    .setLargeThreshold(300)
                    .addEventListeners(new OnReadyListener(), new CommandHandler(),
                                       new NicknameListener(), new OnGuildJoinListener(),
                                       new RegularRoleListener(), new RoleChangeListener(),
                                       new NSFWBotBlockListener(), new MembersCountChannelListener(),
                                       new PreventFalseCommandsListener())
                    .build();


            logger.log("register commands ..");
            CommandHandler.registerCommands();

            logger.log("try to create connection with mongodb ..");
            MongoDB.getInstance();

            logger.log("bot started successfully.");
        }
        return jda;
    }


    public static void main(String[] args) {
        logger.log( "start the klotzscherpub bot .. ");
        getJda();
        AfkHandler.getHandler().startHandler();
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

    // get the time which is need for the regular role
    public static long getNeededTimeForRegularRole() {
        return neededTimeForRegularRole;
    }
}
