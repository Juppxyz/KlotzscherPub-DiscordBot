package xyz.jupp.discord.commands;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.jupp.discord.commands.handler.Command;
import xyz.jupp.discord.core.KlotzscherPub;
import xyz.jupp.discord.core.KlotzscherPubGuild;
import xyz.jupp.discord.utils.PrivateChannelBuilder;

public class HelpCommand implements Command {

    // logger
    private final static Logger log = LoggerFactory.getLogger(HelpCommand.class);

    @Override
    public void action(String[] args, GuildMessageReceivedEvent event) {
        Member member = event.getMember();
        log.info(KlotzscherPub.getPrefix() + "execute help command (" + member.getId() +")");
        PrivateChannelBuilder privateChannelBuilder = new PrivateChannelBuilder(getHelpMessage(), PrivateChannelBuilder.PrivateChannelType.INFO);
        privateChannelBuilder.sendPrivateMessage(event.getAuthor());
    }

    private String getHelpMessage(){
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("⚙️ Hier findest du alles was der KlotzscherPub kann\n\n\n");
        stringBuilder.append(" » ").append(KlotzscherPub.getChatPrefix()).append("active - Zeigt wie lange du schon aktiv auf dem Discord bist.\n\n");
        stringBuilder.append(" ❓ Fragen einfach direkt in " + KlotzscherPubGuild.getGuild().getTextChannelById(795763625017081856L).getAsMention());
        return stringBuilder.toString();
    }

    @Override
    public String getCommand() {
        return "help";
    }
}
