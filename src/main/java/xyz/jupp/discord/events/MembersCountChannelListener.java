package xyz.jupp.discord.events;

import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import xyz.jupp.discord.core.KlotzscherPubGuild;
import xyz.jupp.discord.log.LoggerUtil;

public class MembersCountChannelListener extends ListenerAdapter {

    // channel name:
    // » Mitglieder: ZAHL

    private static final LoggerUtil logger = new LoggerUtil(MembersCountChannelListener.class.getSimpleName());

    @Override
    public void onGuildVoiceJoin(@NotNull GuildVoiceJoinEvent event) {
        updateMemberCountChannel();
    }

    @Override
    public void onGuildLeave(@NotNull GuildLeaveEvent event) {
        updateMemberCountChannel();
    }

    public static void updateMemberCountChannel() {
        long amountOfMembers = KlotzscherPubGuild.getGuild().getMemberCache().size();
        String placeholder = "» Mitglieder: " + amountOfMembers;
        logger.log("updated name for members-count channel", String.valueOf(amountOfMembers));
        KlotzscherPubGuild.getMemberCountChannel().getManager().setName(placeholder).queue();
    }

}
