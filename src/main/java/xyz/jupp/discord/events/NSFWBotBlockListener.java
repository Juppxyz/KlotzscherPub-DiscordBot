package xyz.jupp.discord.events;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class NSFWBotBlockListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if ((event.getAuthor().isBot() && event.getAuthor().getIdLong() == 285480424904327179L) && event.getChannel().getIdLong() != 925433201348976681L){
            event.getMessage().delete().queue();
        }

    }
}
