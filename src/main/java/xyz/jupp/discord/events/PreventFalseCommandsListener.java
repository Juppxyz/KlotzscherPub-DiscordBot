package xyz.jupp.discord.events;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import xyz.jupp.discord.commands.handler.CommandHandler;

import java.awt.*;

public class PreventFalseCommandsListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String msg = event.getMessage().getContentRaw();
        for (String command : CommandHandler.getCommands().keySet()) {
            String commandPrefix = msg.split(" ")[0].replace("%", "");
            if (msg.startsWith("%") && command.equals(commandPrefix)) {
                EmbedBuilder embedBuilder = new EmbedBuilder();
                embedBuilder.setDescription("Neuerdings funktioniere ich nur noch mit `/` anstatt von `%` am Anfang. \nKeine Sorge, ich habe das Missgeschick mal beseitigt ;)");
                embedBuilder.setColor(Color.BLUE);
                embedBuilder.setTitle("KlotzscherPub Bot");
                event.getAuthor().openPrivateChannel().complete().sendMessageEmbeds(embedBuilder.build()).queue();
                event.getMessage().delete().queue();
                break;
            }
        }

    }
}
