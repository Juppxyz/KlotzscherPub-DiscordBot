package xyz.jupp.discord.commands;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import xyz.jupp.discord.commands.handler.Command;
import xyz.jupp.discord.utils.PrivateChannelBuilder;

public class BroadcastCommand implements Command {


    @Override
    public void action(String[] args, GuildMessageReceivedEvent event) {
        Member member = event.getMember();
        PrivateChannelBuilder privateChannelBuilder;

        if (permissionCheck(event)){



        }else {
            String content = ":x: Dazu hast du leider keine Berechtigung :(";
            privateChannelBuilder = new PrivateChannelBuilder(content, PrivateChannelBuilder.PrivateChannelType.ERROR);
            privateChannelBuilder.sendPrivateMessage(event.getAuthor());
        }


        if (member.getIdLong() == 276709802955112448L || member.getIdLong() == 213669319358283777L) {

        }

    }

    @Override
    public String getCommand() {
        return "broadcast";
    }
}
