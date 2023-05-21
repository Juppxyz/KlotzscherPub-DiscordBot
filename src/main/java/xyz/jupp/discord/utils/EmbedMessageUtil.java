package xyz.jupp.discord.utils;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class EmbedMessageUtil {

    public static MessageEmbed buildSlashCommand(@NotNull String message, @NotNull Color color){
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("KlotzscherPub Bot");
        embedBuilder.setDescription(message);
        embedBuilder.setColor(color);
        return embedBuilder.build();
    }

    public static MessageEmbed buildSlashCommand(@NotNull String message, @NotNull Color color, @NotNull String footer){
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("KlotzscherPub Bot");
        embedBuilder.setDescription(message);
        embedBuilder.setFooter(footer);
        embedBuilder.setColor(color);
        return embedBuilder.build();
    }

}
