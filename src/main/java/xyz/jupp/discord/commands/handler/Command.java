package xyz.jupp.discord.commands.handler;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public interface Command {

    default boolean permissionCheck(GuildMessageReceivedEvent event){
        Member member = event.getMember();

        long bossId = 628250586806091816L;
        long managerId = 628301763765862429L;

        for (Role role : member.getRoles()){
            if (role.getIdLong() == bossId || role.getIdLong() == managerId){
                return true;
            }
        }

        return false;
    }


    void action(String[] args, GuildMessageReceivedEvent event);
    String getCommand();

}
