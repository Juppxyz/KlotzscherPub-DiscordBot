package xyz.jupp.discord.commands;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import xyz.jupp.discord.commands.handler.Command;
import xyz.jupp.discord.utils.Message;

public class NicknameResetCommand implements Command {


    @Override
    public void action(String[] args, GuildMessageReceivedEvent event) {
        if (permissionCheck(event)){
            Member member = event.getMember();
            if (member != null){
                Message.send(event.getChannel(), "name: " + event.getAuthor().getName());
            }
        }
    }

    @Override
    public String getCommand() {
        return "nickname";
    }
}
