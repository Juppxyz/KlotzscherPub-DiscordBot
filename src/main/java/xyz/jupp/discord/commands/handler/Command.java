package xyz.jupp.discord.commands.handler;

import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public interface Command {

    default boolean permissionCheck(GuildMessageReceivedEvent e){
        if (e.getChannel().getId().equals("796428306228052010")){
            List<Role> roleList = e.getGuild().getRoles();
            for (Role role : roleList) {
                if (role.getName().equalsIgnoreCase("manager") || role.getName().equalsIgnoreCase("boss")){
                    return true;
                }
            }
        }
        return false;
    }


    void action(String[] args, GuildMessageReceivedEvent e);
    String getCommand();

}
