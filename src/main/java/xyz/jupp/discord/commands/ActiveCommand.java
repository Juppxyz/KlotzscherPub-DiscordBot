package xyz.jupp.discord.commands;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.jupp.discord.commands.handler.Command;
import xyz.jupp.discord.core.KlotzscherPub;
import xyz.jupp.discord.database.RegularCollection;
import xyz.jupp.discord.utils.EmbedMessageBuilder;

import java.util.concurrent.TimeUnit;

public class ActiveCommand implements Command {

    // logger
    private final static Logger log = LoggerFactory.getLogger(ActiveCommand.class);

    @Override
    public void action(String[] args, GuildMessageReceivedEvent event) {
        Member member = event.getMember();
        log.info(KlotzscherPub.getPrefix() + "execute active command (" + member.getId() + ")");
        RegularCollection regularCollection = new RegularCollection(member);


        long activeTime = TimeUnit.MILLISECONDS.toHours(regularCollection.getActiveTime());
        String timeText = activeTime == 1 ? "1 Stunde" : activeTime + " Stunden";

        EmbedMessageBuilder embedBuilder = new EmbedMessageBuilder("⏱️ Deine aktive Zeit beträgt: " + timeText, EmbedMessageBuilder.EmbedMessageTypes.INFO);
        event.getChannel().sendMessageEmbeds(embedBuilder.getMessage(member.getEffectiveName()).build()).queue();

 }

    @Override
    public String getCommand() {
        return "active";
    }
}
