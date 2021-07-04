package xyz.jupp.discord.utils;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class Message {

    private static EmbedBuilder embedBuilder = null;
    private EmbedBuilder embedBuilder(){
        if (embedBuilder == null){
            embedBuilder = new EmbedBuilder();

            embedBuilder.setColor(Color.ORANGE);
            embedBuilder.setTitle("KlotzscherPub Bot » ");
            embedBuilder.setAuthor("DiscordBot OpenSource: ", "");
        }

    }

    public static void send(@NotNull MessageReceivedEvent event, @NotNull String content){
        EmbedBuilder embedBuilder = new EmbedBuilder();
    }

}