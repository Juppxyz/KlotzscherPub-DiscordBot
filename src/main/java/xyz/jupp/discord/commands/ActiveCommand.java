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
import java.util.concurrent.TimeUnit;

public class ActiveCommand implements Command {

    // logger
    private final static Logger log = LoggerFactory.getLogger(ActiveCommand.class);

    @Override
    public void action(String[] args, GuildMessageReceivedEvent event) {
        Member member = event.getMember();
        log.info(KlotzscherPub.getPrefix() + "execute active command (" + member.getId() + ")");
        RegularCollection regularCollection = new RegularCollection(member);

        EmbedMessageBuilder embedBuilder;

        if (args.length == 1 && args[0].equalsIgnoreCase("top")){
            HashMap<String, Long> userActiveTimesMap = regularCollection.getAllActiveTimesFromUsers();
            Long max = Collections.max(userActiveTimesMap.values());

            for (Map.Entry<String, Long> entry : userActiveTimesMap.entrySet()){
                if (entry.getValue() == max){
                    embedBuilder = new EmbedMessageBuilder("\uD83C\uDFC6 King of the Hill ist: " + entry.getKey(), EmbedMessageBuilder.EmbedMessageTypes.INFO);
                    event.getChannel().sendMessageEmbeds(embedBuilder.getMessage(null).build()).queue();
                    break;

                }
            }

        }else{
            long activeTime = TimeUnit.MILLISECONDS.toHours(regularCollection.getActiveTime());
            String timeTextEnd = activeTime == 1 ? "1 Stunde" : activeTime + " Stunden";

            embedBuilder = new EmbedMessageBuilder("⏱️ Deine aktive Zeit beträgt: " + timeTextEnd, EmbedMessageBuilder.EmbedMessageTypes.INFO);
            event.getChannel().sendMessageEmbeds(embedBuilder.getMessage(member.getNickname()).build()).queue();

        }

 }

    @Override
    public String getCommand() {
        return "active";
    }
}
