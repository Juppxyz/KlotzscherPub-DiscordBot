package xyz.jupp.discord.utils;

import net.dv8tion.jda.api.EmbedBuilder;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class EmbedMessageBuilder {

    // knowledge variables

    private String content;
    private EmbedMessageTypes embedMessageTypes;

    public EmbedMessageBuilder(@NotNull String content, @NotNull EmbedMessageTypes embedMessageTypes){
        this.embedMessageTypes = embedMessageTypes;
        this.content = content;
    }



    public EmbedBuilder getMessage(){
        EmbedBuilder embedBuilder = new EmbedBuilder();
        Color color = Color.green;
        String footer = "";

        if (embedMessageTypes == EmbedMessageTypes.INFO){
            color = Color.BLUE;
            footer = "";
        }else if (embedMessageTypes == EmbedMessageTypes.BROADCAST){
            color = Color.ORANGE;
        }else if (embedMessageTypes == EmbedMessageTypes.ERROR){
            color = Color.RED;
        }

        embedBuilder.setDescription(getContent());
        embedBuilder.setColor(color);

        embedBuilder.setTitle("KlotzscherPub Bot");
        embedBuilder.setImage("https://cdn.discordapp.com/icons/628250514756468760/a_f72086c4c399ddd5518bdccc732910b1.png?size=128");
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

        INFO, ERROR, BROADCAST

    }
}
