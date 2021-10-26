package xyz.jupp.discord.core;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;

import static xyz.jupp.discord.core.KlotzscherPub.getJda;

public class KlotzscherPubGuild {

    private final static Guild klotzscherPubGuild = getJda().getGuildById(628250514756468760L);
    private final static TextChannel mainTextChannel = getGuild().getTextChannelById(795763625017081856L);

    public synchronized static Guild getGuild() {
        return klotzscherPubGuild;
    }

    // labern
    public static TextChannel getMainTextChannel() {
        return mainTextChannel;
    }
}
