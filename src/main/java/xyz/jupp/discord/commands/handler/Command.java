package xyz.jupp.discord.commands.handler;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public interface Command {

    default boolean permissionCheck(MessageReceivedEvent event){
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


    void action(String[] args, MessageReceivedEvent event);
    String getCommand();

}
