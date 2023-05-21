package xyz.jupp.discord.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import xyz.jupp.discord.commands.handler.Command;
import xyz.jupp.discord.commands.handler.CommandOptions;
import xyz.jupp.discord.database.RegularCollection;
import xyz.jupp.discord.events.RegularRoleListener;

import java.awt.*;
import java.util.Date;

public class SaveCurrentTimesCommand implements Command {

    @Override
    public void action(SlashCommandInteractionEvent event) {
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
            regularCollection.updateDatetime(activeTime, tmpMember.getEffectiveName());
        }
        MessageEmbed messageEmbed = new EmbedBuilder().setDescription("fertig").setColor(Color.BLUE).build();

        event.reply("fertig").addEmbeds(messageEmbed).setEphemeral(true).queue();
    }

    @Override
    public CommandOptions getCommandOptions() {
        return new CommandOptions("savetime", "Speichert die Zeit aller aktiven Spieler.");
    }
}
