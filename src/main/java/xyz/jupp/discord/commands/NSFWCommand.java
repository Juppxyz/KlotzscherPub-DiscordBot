package xyz.jupp.discord.commands;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.UserSnowflake;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import xyz.jupp.discord.commands.handler.Command;
import xyz.jupp.discord.commands.handler.CommandOptions;
import xyz.jupp.discord.core.KlotzscherPubGuild;
import xyz.jupp.discord.log.LoggerUtil;
import xyz.jupp.discord.utils.EmbedMessageUtil;

import java.awt.*;

public class NSFWCommand implements Command {

    @Override
    public void action(SlashCommandInteractionEvent event) {
        Member member = event.getMember();
        Role nsfwRole = KlotzscherPubGuild.getGuild().getRoleById(925430805944221726L);
        String response = "";
        if (!member.getRoles().contains(nsfwRole)){
            KlotzscherPubGuild.getGuild().addRoleToMember(UserSnowflake.fromId(member.getIdLong()), nsfwRole).queue();
            new LoggerUtil(NSFWCommand.class.getSimpleName()).log("add nsfw role", member.getId());
           response = "Du hast nun Zugriff auf den NSFW Kanal. Viel Spaß ;)";
        }else {
            response = "Du hast bereits Zugriff auf den NSFW Kanal.";
        }
        event.replyEmbeds(EmbedMessageUtil.buildSlashCommand(response, Color.BLUE)).setEphemeral(true).queue();
    }

    @Override
    public CommandOptions getCommandOptions() {
        return new CommandOptions("gibsmir", "Gibt dir die NSFW Rolle.");
    }
}
