package xyz.jupp.discord.commands;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;
import xyz.jupp.discord.commands.handler.Command;
import xyz.jupp.discord.utils.PrivateChannelBuilder;

public class StreamingCommand implements Command {

    @Override
    public void action(String[] args, GuildMessageReceivedEvent event) {
        Member member = event.getMember();

        if (!member.getVoiceState().inVoiceChannel()){
            String errorMsg = "Du musst in einem Channel sein um diesen Befehl ausführen zu können.";
            PrivateChannelBuilder privateChannelBuilder = new PrivateChannelBuilder(errorMsg, PrivateChannelBuilder.PrivateChannelType.ERROR);
            privateChannelBuilder.sendPrivateMessage(member.getUser());
        }else if (member.getVoiceState().inVoiceChannel()
                && (member.getVoiceState().getChannel().getMembers().size() < 2)){
            String errorMsg = "Du musst in einem Channel, mit mehr als 2 Personen sein um diesen Befehl ausführen zu können.";
            PrivateChannelBuilder privateChannelBuilder = new PrivateChannelBuilder(errorMsg, PrivateChannelBuilder.PrivateChannelType.ERROR);
            privateChannelBuilder.sendPrivateMessage(member.getUser());
        }else {
            AudioManager audioManager = event.getGuild().getAudioManager();
            audioManager.openAudioConnection(member.getVoiceState().getChannel());
        }

    }



    // disconnect the bot from the voice channel
    public static void disconnectBot() {

    }


    @Override
    public String getCommand() {
        return null;
    }
}
