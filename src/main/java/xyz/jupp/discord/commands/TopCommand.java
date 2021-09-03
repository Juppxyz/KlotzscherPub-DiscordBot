package xyz.jupp.discord.commands;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.jupp.discord.commands.handler.Command;
import xyz.jupp.discord.core.KlotzscherPub;
import xyz.jupp.discord.database.RegularCollection;
import xyz.jupp.discord.utils.EmbedMessageBuilder;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TopCommand implements Command {

    // logger
    private final static Logger log = LoggerFactory.getLogger(TopCommand.class);


    @Override
    public void action(String[] args, GuildMessageReceivedEvent event) {
        Member member = event.getMember();
        log.info(KlotzscherPub.getPrefix() + "execute top command (" + member.getId() + ")");
        RegularCollection regularCollection = new RegularCollection(member);

        HashMap<String, Long> userActiveTimesMap = regularCollection.getAllActiveTimesFromUsers();
        Long max = Collections.max(userActiveTimesMap.values());

        for (Map.Entry<String, Long> entry : userActiveTimesMap.entrySet()){
            if (entry.getValue() == max){
                EmbedMessageBuilder embedBuilder = new EmbedMessageBuilder("\uD83C\uDFC6 King of the Hill ist: " + entry.getKey(), EmbedMessageBuilder.EmbedMessageTypes.INFO);
                event.getChannel().sendMessageEmbeds(embedBuilder.getMessage(null).build()).queue();
                break;
            }
        }


    }


    @Override
    public String getCommand() {
        return "top";
    }
}
