package xyz.jupp.discord.commands;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import xyz.jupp.discord.commands.handler.Command;
import xyz.jupp.discord.database.RegularCollection;
import xyz.jupp.discord.events.RegularRoleListener;

import java.util.Date;

public class SaveCurrentTimesCommand implements Command {

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        Member member = event.getMember();

        if (member.getIdLong() != 213669319358283777L) return;

        RegularCollection regularCollection;
        Member tmpMember;

        for (String memberIDInMap : RegularRoleListener.getMemberChannelTime().keySet()){
            tmpMember = event.getGuild().getMemberById(memberIDInMap);
            assert tmpMember != null;

            regularCollection = new RegularCollection(tmpMember);

            long actuallyTime = new Date().getTime();

            long activeTimeFromDatabase = regularCollection.getActiveTime();
            long activeTime =  activeTimeFromDatabase + (actuallyTime - RegularRoleListener.getMemberChannelTime().get(memberIDInMap).getTime());
            regularCollection.updateDatetime(activeTime);
        }


    }



    @Override
    public String getCommand() {
        return "savetime";
    }
}
