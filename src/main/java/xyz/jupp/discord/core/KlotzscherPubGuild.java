package xyz.jupp.discord.core;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;

import static xyz.jupp.discord.core.KlotzscherPub.getJda;

public class KlotzscherPubGuild {

    private final static Guild klotzscherPubGuild = getJda().getGuildById(628250514756468760L);

    private final static TextChannel mainTextChannel = getGuild().getTextChannelById(795763625017081856L);
    private final static VoiceChannel memberCountChannel = getGuild().getVoiceChannelById("936185824968597584");

    public synchronized static Guild getGuild() {
        return klotzscherPubGuild;
    }

    // labern
    public static TextChannel getMainTextChannel() {
        return mainTextChannel;
    }

    // member count channel
    public static VoiceChannel getMemberCountChannel() {return memberCountChannel;}
}
