package xyz.jupp.discord.commands;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import xyz.jupp.discord.commands.handler.Command;
import xyz.jupp.discord.commands.handler.CommandOptions;
import xyz.jupp.discord.database.RegularCollection;
import xyz.jupp.discord.log.LoggerUtil;
import xyz.jupp.discord.utils.EmbedMessageUtil;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class TopCommand implements Command {

    // logger
    private final static LoggerUtil logger = new LoggerUtil(TopCommand.class.getSimpleName());

    @Override
    public void action(SlashCommandInteractionEvent event) {
        Member member = event.getMember();
        logger.log("execute top command", member.getId());
        RegularCollection regularCollection = new RegularCollection(member);
        String[] userActiveTimesMap = regularCollection.getMostActiveTimeUser();

        String member_name = userActiveTimesMap[0];
        String active_time = userActiveTimesMap[1];

        long activeTime = TimeUnit.MILLISECONDS.toHours(Long.parseLong(active_time));
        String timeText = activeTime == 1 ? "1 Stunde" : activeTime + " Stunden";
        event.replyEmbeds(EmbedMessageUtil.buildSlashCommand("\uD83C\uDFC6 King of the Hill ist: " + member_name+ ", mit stolzen " + timeText + "!", Color.BLUE)).queue();
    }

    @Override
    public CommandOptions getCommandOptions() {
        return new CommandOptions("top", "Zeigt den User mit der größten aktiven Zeit an.");
    }
}
