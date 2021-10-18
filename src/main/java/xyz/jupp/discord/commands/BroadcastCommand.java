package xyz.jupp.discord.commands;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import xyz.jupp.discord.commands.handler.Command;

import java.util.Arrays;

public class BroadcastCommand implements Command {


    @Override
    public void action(String[] args, GuildMessageReceivedEvent event) {
        Member member = event.getMember();
        System.out.println(Arrays.toString(args));


        if (member.getIdLong() == 276709802955112448L || member.getIdLong() == 213669319358283777L) {

        }

    }

    @Override
    public String getCommand() {
        return "broadcast";
    }
}
