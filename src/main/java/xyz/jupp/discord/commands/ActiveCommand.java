package xyz.jupp.discord.commands;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.jupp.discord.commands.handler.Command;
import xyz.jupp.discord.core.KlotzscherPub;
import xyz.jupp.discord.database.RegularCollection;
import xyz.jupp.discord.utils.EmbedMessageBuilder;

public class ActiveCommand implements Command {

    // logger
    private final static Logger log = LoggerFactory.getLogger(ActiveCommand.class);

    @Override
    public void action(String[] args, GuildMessageReceivedEvent event) {
        Member member = event.getMember();
        log.info(KlotzscherPub.getPrefix() + "execute active command (" + member.getId() + ")");

        RegularCollection regularCollection = new RegularCollection(member);
        EmbedMessageBuilder embedMessageBuilder = new EmbedMessageBuilder("Deine aktive Zeit beträgt: " + regularCollection.getActiveTime() + " Stunden", EmbedMessageBuilder.EmbedMessageTypes.INFO);
        event.getChannel().sendMessageEmbeds(embedMessageBuilder.getMessage().build()).queue();
    }

    @Override
    public String getCommand() {
        return "active";
    }
}
