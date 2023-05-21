package xyz.jupp.discord.events;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.unions.AudioChannelUnion;
import net.dv8tion.jda.api.events.guild.voice.GenericGuildVoiceEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import xyz.jupp.discord.core.KlotzscherPub;
import xyz.jupp.discord.core.KlotzscherPubGuild;
import xyz.jupp.discord.database.RegularCollection;
import xyz.jupp.discord.log.LoggerUtil;

import java.awt.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RegularRoleListener extends ListenerAdapter {

    // logger
    private final static LoggerUtil log = new LoggerUtil(RegularRoleListener.class.getSimpleName());

    // is a list for save temporally the member time in channel
    private final static Map<String, Date> memberChannelTime = new HashMap<>();

    private final static Long afkChannelID = 628525193236840458L;

    //@Override
    //public void onGuildVoiceJoin(GuildVoiceJoinEvent event) {
    //    Member member = event.getMember();
    //    VoiceChannel channel = KlotzscherPubGuild.getGuild().getVoiceChannelById(628525193236840458L);
    //    if (!(event.getMember().getUser().isBot()) && !(event.getChannelJoined().getIdLong() == channel.getIdLong())){
    //        if (!memberChannelTime.containsKey(member.getId())){
    //            memberChannelTime.put(member.getId(), new Date());
    //        }
    //    }
//
    //}
    //
//
    //@Override
    //public void onGuildVoiceLeave(GuildVoiceLeaveEvent event) {
    //    Member member = event.getMember();
    //    if (memberChannelTime.containsKey(member.getId())) {
    //        RegularCollection regularCollection = new RegularCollection(event.getMember());
    //        if (regularCollection.existMemberInDatabase()){
//
    //            long dateFromMember = memberChannelTime.get(member.getId()).getTime();
    //            long actuallyTime = new Date().getTime();
//
    //            long activeTimeFromDatabase = regularCollection.getActiveTime();
    //            long activeTime =  activeTimeFromDatabase + (actuallyTime - dateFromMember);
//
    //            regularCollection.updateDatetime(activeTime, member.getEffectiveName());
//
    //            // check if the user has the regular role time reached
    //            if(activeTimeFromDatabase >= KlotzscherPub.getNeededTimeForRegularRole()) {
    //                if (updateRole(member, event.getGuild())){
    //                    EmbedMessageBuilder embedMessageBuilder = new EmbedMessageBuilder(member.getAsMention() + " ist nun ein Stammkunde. Glückwunsch!", EmbedMessageBuilder.EmbedMessageTypes.BROADCAST);
    //                    KlotzscherPubGuild.getMainTextChannel().sendMessageEmbeds(embedMessageBuilder.getMessage(null).build()).queue();
    //                }
    //            }
//
    //        }else {
    //            regularCollection.createNewMemberInDatabase();
    //        }
//
    //        memberChannelTime.remove(member.getId());
//
    //    }
//
    //}


    @Override
    public void onGenericGuildVoice(GenericGuildVoiceEvent event) {
        boolean isChannelJoined         = event.getVoiceState().inAudioChannel();
        AudioChannelUnion voiceChannel  = event.getVoiceState().getChannel();
        Member member                   = event.getMember();

        // check if the user is a bot or system (game activities)
        if (member.getUser().isBot() || member.getUser().isSystem()) return;

        // important!
        // if the player switch the channel directly, the state is always true
        // check if the player is switching the channel directly
        if (isChannelJoined && memberChannelTime.containsKey(member.getId())) return;

        if (isChannelJoined) {
            // check if channel is afk space
            if (voiceChannel.getIdLong() == afkChannelID) return;
            memberChannelTime.put(member.getId(), new Date());
        }else {
            if (!memberChannelTime.containsKey(member.getId())) return;
            RegularCollection regularCollection = new RegularCollection(event.getMember());
            if (regularCollection.existMemberInDatabase()){
                long dateFromMember = memberChannelTime.get(member.getId()).getTime();
                long actuallyTime = new Date().getTime();
                long activeTimeFromDatabase = regularCollection.getActiveTime();
                long activeTime =  activeTimeFromDatabase + (actuallyTime - dateFromMember);
                regularCollection.updateDatetime(activeTime, member.getEffectiveName());

                // check if the user has the regular role time reached
                if(activeTimeFromDatabase >= KlotzscherPub.getNeededTimeForRegularRole()) {
                    if (updateRole(member, event.getGuild())){
                        EmbedBuilder embedBuilder = new EmbedBuilder();
                        embedBuilder.setTitle("KlotzscherPub Bot");
                        embedBuilder.setColor(Color.YELLOW);
                        embedBuilder.setDescription(member.getAsMention() + " ist nun ein Stammkunde. Glückwunsch!");
                        KlotzscherPubGuild.getMainTextChannel().sendMessageEmbeds(embedBuilder.build()).queue();
                    }
                }
            }else {
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

    // this method loads all currently active members in the memberChannelTime map
    public static void loadAllMembers() {
        log.log("load all active members..");
        for (Member member : KlotzscherPubGuild.getGuild().getMembers()){
            if (member.getVoiceState() == null || member.getUser().isBot()) continue;
            if (member.getVoiceState().inAudioChannel()){
                log.log(String.format("load member: %s (%s)", member.getEffectiveName(),member.getId()));
                memberChannelTime.put(member.getId(), new Date());
            }
        }
        log.log("all members were loaded ");
    }


    public static Map<String, Date> getMemberChannelTime() {
        return memberChannelTime;
    }


}
