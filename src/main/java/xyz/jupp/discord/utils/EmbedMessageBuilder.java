package xyz.jupp.discord.utils;

import net.dv8tion.jda.api.EmbedBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

public class EmbedMessageBuilder {

    // knowledge variables

    private String content;
    private EmbedMessageTypes embedMessageTypes;

    public EmbedMessageBuilder(@NotNull String content, @NotNull EmbedMessageTypes embedMessageTypes){
        this.embedMessageTypes = embedMessageTypes;
        this.content = content;
    }



    public EmbedBuilder getMessage(@Nullable String footer){
        EmbedBuilder embedBuilder = new EmbedBuilder();
        Color color = Color.green;

        if (getEmbedMessageTypes() == EmbedMessageTypes.INFO){
            color = Color.BLUE;
        }else if (getEmbedMessageTypes() == EmbedMessageTypes.BROADCAST){
            color = Color.ORANGE;
        }else if (getEmbedMessageTypes() == EmbedMessageTypes.ERROR){
            color = Color.RED;
        }

        if (footer != null){
            embedBuilder.setFooter(footer);
        }

        embedBuilder.setDescription(getContent());
        embedBuilder.setColor(color);

        embedBuilder.setTitle("KlotzscherPub Bot \uD83C\uDF7A");
        //embedBuilder.setImage("https://cdn.discordapp.com/icons/628250514756468760/a_f72086c4c399ddd5518bdccc732910b1.png?size=32");
        return embedBuilder;
    }



    // Getter
    public EmbedMessageTypes getEmbedMessageTypes() {
        return embedMessageTypes;
    }

    public String getContent() {
        return content;
    }



    public enum EmbedMessageTypes {

        INFO, ERROR, BROADCAST, WARN

    }
}
