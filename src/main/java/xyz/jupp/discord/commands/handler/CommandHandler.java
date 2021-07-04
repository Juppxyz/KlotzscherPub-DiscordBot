package xyz.jupp.discord.commands.handler;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import xyz.jupp.discord.core.KlotzscherPub;

import java.util.ArrayList;

public class CommandHandler extends ListenerAdapter {

    private static ArrayList<Command> commands = new ArrayList<>();

    private synchronized static ArrayList<Command> getCommands() {
        return commands;
    }

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
            System.out.println(KlotzscherPub.getPrefix() + "Add new Command: " + cmd);
        }
        return true;
    }
}