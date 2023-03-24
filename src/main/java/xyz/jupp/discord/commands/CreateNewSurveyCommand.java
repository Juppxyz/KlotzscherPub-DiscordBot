package xyz.jupp.discord.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import xyz.jupp.discord.commands.handler.Command;
import xyz.jupp.discord.core.KlotzscherPubGuild;
import xyz.jupp.discord.database.SurveyCollection;
import xyz.jupp.discord.log.LoggerUtil;
import xyz.jupp.discord.utils.PrivateChannelBuilder;

import java.awt.*;


public class CreateNewSurveyCommand implements Command {

    private final static LoggerUtil logger = new LoggerUtil(CreateNewSurveyCommand.class.getSimpleName());

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        Member member = event.getMember();
        if (member.getRoles().size() == 1 && member.getRoles().contains(KlotzscherPubGuild.getGuild().getRoleById(628504141030752256L))) {
            PrivateChannelBuilder privateChannelBuilder = new PrivateChannelBuilder("Du kannst derzeit leider noch keine Umfragen starten.", PrivateChannelBuilder.PrivateChannelType.WARN);
            privateChannelBuilder.sendPrivateMessage(event.getAuthor());
            return;
        }

        args[0] = "";
        StringBuilder content = new StringBuilder();
        for (String s : args) {
            content.append(" ");
            content.append(s);
        }

        if (content.length() > 900) {
            PrivateChannelBuilder privateChannelBuilder = new PrivateChannelBuilder("Probiere deine Umfrage bitte etwas kürzer zu gestalten.", PrivateChannelBuilder.PrivateChannelType.WARN);
            privateChannelBuilder.sendPrivateMessage(event.getAuthor());
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
            msg.addReaction("U+2705").queue();
            msg.addReaction("U+274C").queue();

            SurveyCollection surveyCollection = new SurveyCollection(msg.getId());
            surveyCollection.createNewSurvey();
        });

    }

    @Override
    public String getCommand() {
        return "umfrage";
    }

}
