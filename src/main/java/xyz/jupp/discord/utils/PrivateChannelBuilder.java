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

    public PrivateChannelBuilder(@NotNull String content, @NotNull PrivateChannelType privateChannelType) {
        this.privateChannelType = privateChannelType;
        this.content = content;
    }

    public void sendPrivateMessage(@NotNull User user){
        log.log("send private message", user.getId());

        EmbedMessageBuilder embedMessageBuilder = null;
        if (privateChannelType == PrivateChannelType.ERROR){
            embedMessageBuilder = new EmbedMessageBuilder(content, EmbedMessageBuilder.EmbedMessageTypes.ERROR);
        }else {
            embedMessageBuilder = new EmbedMessageBuilder(content, EmbedMessageBuilder.EmbedMessageTypes.INFO);
        }

        user.openPrivateChannel().complete().sendMessageEmbeds(embedMessageBuilder.getMessage(null).build()).queue();
    }


    public enum PrivateChannelType {

        INFO, ERROR, WARN

    }

}
