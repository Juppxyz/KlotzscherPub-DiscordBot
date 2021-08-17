package xyz.jupp.discord.utils;

import net.dv8tion.jda.api.entities.User;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.jupp.discord.core.KlotzscherPub;

public class PrivateChannelBuilder {

    // logger
    private final static Logger log = LoggerFactory.getLogger(PrivateChannelBuilder.class);

    // knowledge variables
    private String content;
    private PrivateChannelType privateChannelType;

    public PrivateChannelBuilder(@NotNull String content, @NotNull PrivateChannelType privateChannelType) {
        this.privateChannelType = privateChannelType;
        this.content = content;
    }

    public void sendPrivateMessage(@NotNull User user){
        log.info(KlotzscherPub.getPrefix() + "send private message to " + user.getId());
        EmbedMessageBuilder embedMessageBuilder = new EmbedMessageBuilder(content, EmbedMessageBuilder.EmbedMessageTypes.INFO);
        user.openPrivateChannel().complete().sendMessageEmbeds(embedMessageBuilder.getMessage().build()).queue();
    }


    public enum PrivateChannelType {

        INFO, ERROR, WARN

    }

}
