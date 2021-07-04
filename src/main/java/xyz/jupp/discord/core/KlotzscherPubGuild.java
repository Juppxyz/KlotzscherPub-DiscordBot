package xyz.jupp.discord.core;

import net.dv8tion.jda.api.entities.Guild;

import static xyz.jupp.discord.core.KlotzscherPub.getJda;

public class KlotzscherPubGuild {

    private final static Guild klotzscherPubGuild = getJda().getGuildById("628250514756468760");

    public static Guild getGuild() {
        return klotzscherPubGuild;
    }

}
