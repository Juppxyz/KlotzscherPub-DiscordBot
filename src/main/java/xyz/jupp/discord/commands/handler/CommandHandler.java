package xyz.jupp.discord.commands.handler;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.jupp.discord.core.KlotzscherPub;

import java.util.ArrayList;

public class CommandHandler extends ListenerAdapter {

    private static ArrayList<Command> commands = new ArrayList<>();

    private synchronized static ArrayList<Command> getCommands() {
        return commands;
    }

    private static final Logger log = LoggerFactory.getLogger(CommandHandler.class);

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        String msg = event.getMessage().getContentRaw();

        if (msg.startsWith(KlotzscherPub.getChatPrefix())) {
            msg = msg.replace(KlotzscherPub.getChatPrefix(), "");
            String[] args = msg.split(" ");

            for (Command command : getCommands()) {
                if (command.getCommand().equalsIgnoreCase(msg)) {
                    command.action(args, event);
                    event.getMessage().delete().queue();
                }

            }
        }
    }

    public static boolean addCommand(Command cmd) {
        if (cmd != null) {
            getCommands().add(cmd);
            log.info(KlotzscherPub.getPrefix() + "Add new Command: " + cmd.getCommand());
        }
        return true;
    }
}