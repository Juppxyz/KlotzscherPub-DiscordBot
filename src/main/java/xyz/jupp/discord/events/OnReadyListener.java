package xyz.jupp.discord.events;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.jupp.discord.core.KlotzscherPub;
import xyz.jupp.discord.core.KlotzscherPubGuild;

import java.util.List;

public class OnReadyListener extends ListenerAdapter {

    private final static Logger log = LoggerFactory.getLogger(OnReadyListener.class);


    @Override
    public void onReady(@NotNull ReadyEvent event) {
        if (!checkGuilds()){
            log.error(KlotzscherPub.getPrefix() + "the bot can only be used on the KlotzscherPub discord.");
            KlotzscherPub.shutdown();
            return;
        }

        log.info(KlotzscherPub.getPrefix() + "the bot is only on the KlotzscherPub guild.");

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
