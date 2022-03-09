package xyz.jupp.discord.events;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import xyz.jupp.discord.core.KlotzscherPub;
import xyz.jupp.discord.core.KlotzscherPubGuild;
import xyz.jupp.discord.database.RegularCollection;
import xyz.jupp.discord.log.LoggerUtil;
import xyz.jupp.discord.utils.EmbedMessageBuilder;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RegularRoleListener extends ListenerAdapter {

    // logger
    private final static LoggerUtil log = new LoggerUtil(RegularRoleListener.class.getSimpleName());


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
            log.log("moved into the afk room.", member.getId());
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

                regularCollection.updateDatetime(activeTime);

                // check if the user has the regular role time reached
                if(activeTimeFromDatabase >= KlotzscherPub.getNeededTimeForRegularRole()) {
                    if (updateRole(member, event.getGuild())){
                        EmbedMessageBuilder embedMessageBuilder = new EmbedMessageBuilder(member.getAsMention() + " ist nun ein Stammkunde. Glückwunsch!", EmbedMessageBuilder.EmbedMessageTypes.BROADCAST);
                        KlotzscherPubGuild.getMainTextChannel().sendMessageEmbeds(embedMessageBuilder.getMessage(null).build()).queue();
                    }
                    return;
                }

            }else {
                System.out.println("create new member " + member.getEffectiveName());
                regularCollection.createNewMemberInDatabase();
            }

            memberChannelTime.remove(member.getId());

        }

    }



    /* Updates the group to the regular customer (only fot he G's) */
    private boolean updateRole(@NotNull Member member, @NotNull Guild guild) {
        Role regularRole = guild.getRoleById(628302155782029332L);
        if (!member.getRoles().contains(guild.getRoleById(628302155782029332L))){
            guild.addRoleToMember(member, regularRole).queue();
            log.log("updated role to Stammkunde. ", member.getId());
            return true;
        }
        return false;
    }


    public static Map<String, Date> getMemberChannelTime() {
        return memberChannelTime;
    }


}
