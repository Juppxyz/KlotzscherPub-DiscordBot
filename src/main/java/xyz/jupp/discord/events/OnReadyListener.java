package xyz.jupp.discord.events;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import xyz.jupp.discord.core.KlotzscherPub;
import xyz.jupp.discord.core.KlotzscherPubGuild;
import xyz.jupp.discord.log.LoggerUtil;

import java.util.List;

public class OnReadyListener extends ListenerAdapter {

    private final static LoggerUtil logger = new LoggerUtil(OnReadyListener.class.getSimpleName());


    @Override
    public void onReady(@NotNull ReadyEvent event) {
        if (!checkGuilds()){
            logger.error("the bot can only be used on the KlotzscherPub discord.", null);
            KlotzscherPub.shutdown();
            return;
        }

        logger.log("the bot is only on the KlotzscherPub guild.");

    }


    /** This method ensures that the bot is only on the KlotzscherPub */
    private boolean checkGuilds() {
        List<Guild> listOfGuilds = KlotzscherPub.getJda().getGuilds();
        if (listOfGuilds.size() == 1) {
            Guild guild = listOfGuilds.get(0);
            return guild.getId().equals(KlotzscherPubGuild.getGuild().getId());
        }
        return false;
    }
}
