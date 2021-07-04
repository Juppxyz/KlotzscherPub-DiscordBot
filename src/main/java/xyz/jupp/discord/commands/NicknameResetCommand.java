package xyz.jupp.discord.commands;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import xyz.jupp.discord.commands.handler.Command;

public class NicknameResetCommand implements Command {


    @Override
    public void action(String[] args, GuildMessageReceivedEvent e) {
        if (permissionCheck(e)){



        }
    }

    @Override
    public String getCommand() {
        return null;
    }
}
