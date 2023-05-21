package xyz.jupp.discord.commands.handler;

import org.jetbrains.annotations.NotNull;

public class CommandOptions {

    // knowledge variables
    private String commandName;
    private String description;

    public CommandOptions(@NotNull String commandName, @NotNull String description) {
        this.commandName = commandName;
        this.description = description;
    }


    // getter
    public String getCommandName() {
        return commandName;
    }

    public String getDescription() {
        return description;
    }

}
