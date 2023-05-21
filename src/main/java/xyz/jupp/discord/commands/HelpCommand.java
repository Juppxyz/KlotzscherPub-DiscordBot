package xyz.jupp.discord.commands;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import xyz.jupp.discord.commands.handler.Command;
import xyz.jupp.discord.commands.handler.CommandOptions;
import xyz.jupp.discord.core.KlotzscherPubGuild;
import xyz.jupp.discord.log.LoggerUtil;
import xyz.jupp.discord.utils.EmbedMessageUtil;

import java.awt.*;

public class HelpCommand implements Command {

    // logger
    private final static LoggerUtil log = new LoggerUtil(HelpCommand.class.getSimpleName());

    private String getHelpMessage(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("⚙️ Hier findest du alles was der KlotzscherPub kann\n\n\n");
        stringBuilder.append(" » ").append("/active - Zeigt an, wie lange du schon aktiv auf dem Discord bist.\n\n");
        stringBuilder.append(" » ").append("/top - Zeigt den User mit der größten aktiven Zeit an.\n\n");
        stringBuilder.append(" » ").append("/gibsmir - Gibt dir die NSFW Rolle.\n\n");
        stringBuilder.append(" » ").append("/umfrage [Frage] - Erstellt eine öffentliche Umfrage.\n\n");
        stringBuilder.append(" » ").append("/chatgpt [Text] - Sendet einen Text an ChatGPT (Turbo). \n\n");
        stringBuilder.append(" ❓ Fragen einfach direkt in " + KlotzscherPubGuild.getGuild().getTextChannelById(795763625017081856L).getAsMention());
        return stringBuilder.toString();
    }

    @Override
    public void action(SlashCommandInteractionEvent event) {
        Member member = event.getMember();
        log.log("execute help command", member.getId());
        event.replyEmbeds(EmbedMessageUtil.buildSlashCommand(getHelpMessage(), Color.BLUE)).setEphemeral(true).queue();
    }

    @Override
    public CommandOptions getCommandOptions() {
        return new CommandOptions("help", "Zeigt eine Übersicht aller Befehle des KlotzscherPubs.");
    }
}
