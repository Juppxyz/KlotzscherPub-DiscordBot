package xyz.jupp.discord.utils;

import net.dv8tion.jda.api.entities.User;
import org.jetbrains.annotations.NotNull;
import xyz.jupp.discord.log.LoggerUtil;

public class PrivateChannelBuilder {

    // logger
    private final static LoggerUtil log = new LoggerUtil(PrivateChannelBuilder.class.getSimpleName());

    // knowledge variables
    private String content;
    private PrivateChannelType privateChannelType;
    private String footer;

    public PrivateChannelBuilder(@NotNull String content, @NotNull PrivateChannelType privateChannelType) {
        this.privateChannelType = privateChannelType;
        this.content = content;
    }

    public PrivateChannelBuilder(@NotNull String content, @NotNull PrivateChannelType privateChannelType, @NotNull String footer) {
        this.privateChannelType = privateChannelType;
        this.content = content;
        this.footer = footer;
    }

    public void sendPrivateMessage(@NotNull User user){
        log.log("send private message", user.getId());

        EmbedMessageBuilder embedMessageBuilder = null;
        if (privateChannelType == PrivateChannelType.ERROR){
            embedMessageBuilder = new EmbedMessageBuilder(content, EmbedMessageBuilder.EmbedMessageTypes.ERROR);
        }else {
            embedMessageBuilder = new EmbedMessageBuilder(content, EmbedMessageBuilder.EmbedMessageTypes.INFO);
        }

        user.openPrivateChannel().complete().sendMessageEmbeds(embedMessageBuilder.getMessage(this.footer).build()).queue();
    }


    public enum PrivateChannelType {

        INFO, ERROR, WARN

    }

}
