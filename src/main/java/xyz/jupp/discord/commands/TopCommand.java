package xyz.jupp.discord.commands;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import xyz.jupp.discord.commands.handler.Command;
import xyz.jupp.discord.database.RegularCollection;
import xyz.jupp.discord.log.LoggerUtil;
import xyz.jupp.discord.utils.EmbedMessageBuilder;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class TopCommand implements Command {

    // logger
    private final static LoggerUtil logger = new LoggerUtil(TopCommand.class.getSimpleName());


    @Override
    public void action(String[] args, GuildMessageReceivedEvent event) {
        Member member = event.getMember();
        logger.log("execute top command", member.getId());
        RegularCollection regularCollection = new RegularCollection(member);

        HashMap<String, Long> userActiveTimesMap = regularCollection.getAllActiveTimesFromUsers();
        Long max = Collections.max(userActiveTimesMap.values());

        for (Map.Entry<String, Long> entry : userActiveTimesMap.entrySet()){
            if (entry.getValue() == max){

                long activeTime = TimeUnit.MILLISECONDS.toHours(userActiveTimesMap.get(entry.getKey()));
                String timeText = activeTime == 1 ? "1 Stunde" : activeTime + " Stunden";

                EmbedMessageBuilder embedBuilder = new EmbedMessageBuilder("\uD83C\uDFC6 King of the Hill ist: " + entry.getKey() + ", mit stolzen " + timeText + "!", EmbedMessageBuilder.EmbedMessageTypes.INFO);
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
