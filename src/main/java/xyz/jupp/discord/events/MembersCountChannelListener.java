package xyz.jupp.discord.events;

import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import xyz.jupp.discord.core.KlotzscherPubGuild;

public class MembersCountChannelListener extends ListenerAdapter {

    // channel name:
    // » Mitglieder: ZAHL

    @Override
    public void onGuildVoiceJoin(@NotNull GuildVoiceJoinEvent event) {
        updateMemberCountChannel();
    }

    @Override
    public void onGuildLeave(@NotNull GuildLeaveEvent event) {
        updateMemberCountChannel();
    }

    public static void updateMemberCountChannel() {
        new Thread(() -> {
            String placeholder = "» Mitglieder: " + KlotzscherPubGuild.getGuild().getMemberCache().size();
            KlotzscherPubGuild.getMemberCountChannel().getManager().setName(placeholder).queue();
        }).start();
    }

}
