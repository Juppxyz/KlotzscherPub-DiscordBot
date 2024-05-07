package xyz.jupp.discord.commands;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import xyz.jupp.discord.commands.handler.Command;
import xyz.jupp.discord.commands.handler.CommandOptions;
import xyz.jupp.discord.database.RegularCollection;
import xyz.jupp.discord.utils.EmbedMessageUtil;
import xyz.jupp.discord.utils.PrivacyProtectionUtil;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class ActiveCommand implements Command {

    @Override
    public void action(SlashCommandInteractionEvent event) {
        Member member = event.getMember();

        if (PrivacyProtectionUtil.isMemberProtected(member.getId())) {
            event.replyEmbeds(EmbedMessageUtil.buildSlashCommand(":lock: Aus Datenschutzgründen wird deine aktive Zeit nicht mehr erfasst.", Color.BLUE)).setEphemeral(true).queue();
            return;
        }

        RegularCollection regularCollection = new RegularCollection(member);
        long activeTime = TimeUnit.MILLISECONDS.toHours(regularCollection.getActiveTime());
        String timeText = activeTime == 1 ? "1 Stunde" : activeTime + " Stunden";
        event.replyEmbeds(
                EmbedMessageUtil.buildSlashCommand("⏱️ Deine aktive Zeit beträgt: " + timeText, Color.BLUE)
        ).setEphemeral(true).queue();
    }

    @Override
    public CommandOptions getCommandOptions() {
        return new CommandOptions("active", "Zeigt an, wie lange du schon aktiv auf dem Discord bist.");
    }
}
