package xyz.jupp.discord.commands.handler;


import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.jupp.discord.commands.*;
import xyz.jupp.discord.core.KlotzscherPub;
import xyz.jupp.discord.events.ChatGPTListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static xyz.jupp.discord.core.KlotzscherPub.getJda;


public class CommandHandler extends ListenerAdapter {

    private static final HashMap<String, Command> commands = new HashMap<>();
    public synchronized static HashMap<String, Command> getCommands() {
        return commands;
    }

    private static final Logger log = LoggerFactory.getLogger(CommandHandler.class);

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        String commandInput = event.getName();
        if (commands.containsKey(commandInput)){
            log.info("execute command " + event.getName(), event.getMember().getId());
            commands.get(commandInput).action(event);
        }
    }

    public static void registerCommands() {
        log.info(KlotzscherPub.getPrefix() + "register all commands");
        List<Command> commands = Arrays.asList(
                new SaveCurrentTimesCommand(),
                new ActiveCommand(),
                new HelpCommand(),
                new NSFWCommand(),
                new TopCommand(),
                new CreateNewSurveyCommand(),
                new ChatGPTListener()
        );

        List<SlashCommandData> slashCommands = new ArrayList<>();
        for (Command command : commands) {
            getCommands().put(command.getCommandOptions().getCommandName(), command);
            SlashCommandData commandData = Commands.slash(
                    command.getCommandOptions().getCommandName(),
                    command.getCommandOptions().getDescription()
            ).setGuildOnly(true);

            if (command.getCommandOptions().getCommandName().equals("umfrage")
                    || command.getCommandOptions().getCommandName().equals("chatgpt")){
                commandData.addOption(OptionType.STRING, "text", "Deine Frage", true);
            }
            if (command.getCommandOptions().getCommandName().equals("savetime")) {
                commandData.setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.ADMINISTRATOR));
            }
            if (command.getCommandOptions().getCommandName().equals("play")) {
                commandData.setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.ADMINISTRATOR))
                        .addOption(OptionType.STRING, "url", "Die URL der Audio-Datei");
            }
            slashCommands.add(commandData);
        }
        getJda().updateCommands().addCommands(slashCommands).queue();
    }

}