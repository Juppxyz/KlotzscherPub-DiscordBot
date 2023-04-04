package xyz.jupp.discord.commands;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import xyz.jupp.discord.commands.handler.Command;
import xyz.jupp.discord.database.RegularCollection;
import xyz.jupp.discord.utils.PrivateChannelBuilder;

import java.util.concurrent.TimeUnit;

public class ActiveCommand implements Command {

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        Member member = event.getMember();
        RegularCollection regularCollection = new RegularCollection(member);

        long activeTime = TimeUnit.MILLISECONDS.toHours(regularCollection.getActiveTime());
        String timeText = activeTime == 1 ? "1 Stunde" : activeTime + " Stunden";

        PrivateChannelBuilder privateChannelBuilder = new PrivateChannelBuilder("⏱️ Deine aktive Zeit beträgt: " + timeText, PrivateChannelBuilder.PrivateChannelType.INFO);
        privateChannelBuilder.sendPrivateMessage(member.getUser());

    }


    @Override
    public String getCommand() {
        return "active";
    }
}
