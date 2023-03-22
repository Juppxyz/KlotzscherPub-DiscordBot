package xyz.jupp.discord.events;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DetectCIVCodeListener extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        String msg = event.getMessage().getContentRaw();
        if (msg.length() == 8 && msg.contains("-") ) {
            String patternString = "/.+.+.+-.+.+.+.+/";
            Pattern pattern = Pattern.compile(patternString);

            Matcher m = pattern.matcher(msg);
            if (m.find()) {

            }
        }
    }

}
