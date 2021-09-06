package xyz.jupp.discord.events;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
        VoiceChannel channel = KlotzscherPubGuild.getGuild().getVoiceChannelById(628525193236840458L);

        if (!(event.getMember().getUser().isBot()) && !(event.getChannelJoined().getIdLong() == channel.getIdLong())){
            if (!memberChannelTime.containsKey(member.getId())){
                memberChannelTime.put(member.getId(), new Date());
            }
        }else {
            log.info(KlotzscherPub.getPrefix() + member.getId() + " went into the afk room." );
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

                if (activeTimeFromDatabase < 5259600000L) {

                    regularCollection.updateDatetime(activeTime);

                }else {

                    if (updateRole(member)){
                        EmbedMessageBuilder embedMessageBuilder =
                                new EmbedMessageBuilder(member.getAsMention() + " ist nun ein Stammkunde. Glückwunsch!"
                                        , EmbedMessageBuilder.EmbedMessageTypes.BROADCAST);

                        KlotzscherPubGuild.getMainTextChannel().sendMessageEmbeds(embedMessageBuilder.getMessage(null).build()).queue();

                    }

                }

            }else {
                regularCollection.createNewMemberInDatabase();
            }


            memberChannelTime.remove(member.getId());
        }

    }





    /* Updates the group to the regular customer (only fot he G's) */
    private boolean updateRole(@NotNull Member member) {
        for (Role s : member.getRoles()){
            if (s.getName().equals("Stammkunde")){
                return false;
            }
        }

        KlotzscherPubGuild.getGuild().addRoleToMember(member.getId(), KlotzscherPubGuild.getGuild().getRoleById(628302155782029332L)).complete();
        log.info(KlotzscherPub.getPrefix() + "updated role for " + member.getId() + " to Stammkunde. ");
        return true;
    }

}
