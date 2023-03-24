package xyz.jupp.discord.utils;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class Message {


    private static EmbedBuilder getEmbedBuilder(){
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.ORANGE);
        embedBuilder.setTitle("KlotzscherPub Bot » ");
        return embedBuilder;
    }

    public static void send(@NotNull TextChannel channel, @NotNull String content){
        channel.sendMessageEmbeds(getEmbedBuilder().setDescription(content).build()).queue();
    }

}