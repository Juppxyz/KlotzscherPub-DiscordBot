package xyz.jupp.discord.events;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RegularRoleListener extends ListenerAdapter {

    // logger
    private final static Logger log = LoggerFactory.getLogger(RegularRoleListener.class);

    // is a list for save temporally the member time in channel
    private static final Map<String, Date> memberChannelTime = new HashMap<>();

    @Override
    public void onGuildVoiceJoin(GuildVoiceJoinEvent event) {
        Member member = event.getMember();

        if (!memberChannelTime.containsKey(member.getId())){
            memberChannelTime.put(member.getId(), new Date());
        }

    }



    @Override
    public void onGuildVoiceLeave(GuildVoiceLeaveEvent event) {
        Guild guild = event.getGuild(); // Get the guild that the user joined.
        JDA client = event.getJDA();    // Get the already existing JDA instance.


        /*
        if (memberChannelTime.containsKey(member.getId())) {
            RegularCollection regularCollection = new RegularCollection(event.getMember());
            System.out.println(2);
            if (regularCollection.existMemberInDatabase()){
                long dateFromMember = memberChannelTime.get(member.getId()).getTime();
                long actuallyTime = new Date().getTime();
                long activeTime = actuallyTime - dateFromMember;

                System.out.println(3);
                long activeTimeFromDatabase = regularCollection.getActiveTime();
                if (activeTimeFromDatabase < 15 768 000L){
                    System.out.println(3);
                    regularCollection.updateDatetime(activeTime);
                }else {
                    System.out.println(5);
                    event.getGuild().getTextChannelById(796428306228052010L).sendMessage("Cant update member "
                            + member.getNickname() + " (" + member.getId() + ") in database.").queue();
                }

            }else {
                regularCollection.createNewMemberInDatabase();
            }

        }
        */



    }


}
