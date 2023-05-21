package xyz.jupp.discord.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import xyz.jupp.discord.commands.handler.Command;
import xyz.jupp.discord.commands.handler.CommandOptions;
import xyz.jupp.discord.core.KlotzscherPubGuild;
import xyz.jupp.discord.database.SurveyCollection;
import xyz.jupp.discord.log.LoggerUtil;
import xyz.jupp.discord.utils.EmbedMessageUtil;

import java.awt.*;


public class CreateNewSurveyCommand implements Command {

    private final static LoggerUtil logger = new LoggerUtil(CreateNewSurveyCommand.class.getSimpleName());

    @Override
    public void action(SlashCommandInteractionEvent event) {
        Member member = event.getMember();
        if (member.getRoles().size() == 1 && member.getRoles().contains(KlotzscherPubGuild.getGuild().getRoleById(628504141030752256L))) {
            event.replyEmbeds(
                    EmbedMessageUtil.buildSlashCommand("Du kannst derzeit leider noch keine Umfragen starten.", Color.RED)
            ).setEphemeral(true).queue();
            return;
        }

        String content = event.getOption("text", OptionMapping::getAsString);
        if (content.length() > 900) {
            event.replyEmbeds(
                    EmbedMessageUtil.buildSlashCommand("Probiere deine Umfrage bitte etwas kürzer zu gestalten.", Color.ORANGE)
            ).setEphemeral(true).queue();
            return;
        }

        logger.log("create new survey", member.getId());
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.GREEN);
        embedBuilder.setAuthor("❔");
        embedBuilder.setTitle("Umfrage");
        embedBuilder.setDescription(content);
        embedBuilder.setFooter("Umfrage erstellt von " + member.getEffectiveName());
        event.getChannel().sendMessageEmbeds(embedBuilder.build()).queue((msg) -> {
            msg.addReaction(Emoji.fromUnicode("U+2705")).queue();
            msg.addReaction(Emoji.fromUnicode("U+274C")).queue();
            SurveyCollection surveyCollection = new SurveyCollection(msg.getId());
            surveyCollection.createNewSurvey();
        });
    }

    @Override
    public CommandOptions getCommandOptions() {
        return new CommandOptions("umfrage", "Erstellt eine öffentliche Umfrage.");
    }
}
