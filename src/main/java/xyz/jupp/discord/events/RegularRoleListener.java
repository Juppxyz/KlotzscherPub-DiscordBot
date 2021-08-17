package xyz.jupp.discord.events;

import net.dv8tion.jda.api.EmbedBuilder;
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
import xyz.jupp.discord.core.KlotzscherPub;
import xyz.jupp.discord.core.KlotzscherPubGuild;
import xyz.jupp.discord.database.RegularCollection;
import xyz.jupp.discord.utils.EmbedMessageBuilder;

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
        Member member = event.getMember();

        if (memberChannelTime.containsKey(member.getId())) {
            RegularCollection regularCollection = new RegularCollection(event.getMember());
            if (regularCollection.existMemberInDatabase()){
                long dateFromMember = memberChannelTime.get(member.getId()).getTime();
                long actuallyTime = new Date().getTime();
                long activeTimeFromDatabase = regularCollection.getActiveTime();

                long activeTime =  activeTimeFromDatabase + (actuallyTime - dateFromMember);


                if (activeTimeFromDatabase < 15768000L) {

                    regularCollection.updateDatetime(activeTime);
                }else {

                    EmbedMessageBuilder embedMessageBuilder =
                            new EmbedMessageBuilder(member.getAsMention() + " ist nach Stunden langen saufen zum Stammkunden ernannt wurden!"
                                    , EmbedMessageBuilder.EmbedMessageTypes.BROADCAST);

                    KlotzscherPubGuild.getMainTextChannel().sendMessageEmbeds(embedMessageBuilder.getMessage().build()).queue();
                    updateRole(member);
                }

            }else {
                regularCollection.createNewMemberInDatabase();
            }


            memberChannelTime.remove(member.getId());
        }



    }


    /* Updates the group to the regular customer (only fot he G's) */
    private void updateRole(@NotNull Member member) {
        KlotzscherPubGuild.getGuild().addRoleToMember(member.getId(), KlotzscherPubGuild.getGuild().getRoleById(628302155782029332L));
        log.info(KlotzscherPub.getPrefix() + "updated role for " + member.getId() + " to Stammkunde.");
    }

}
