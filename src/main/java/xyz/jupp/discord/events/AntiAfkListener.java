package xyz.jupp.discord.events;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceSelfMuteEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import xyz.jupp.discord.tasks.AntiAfkTask;

import java.util.Date;

public class AntiAfkListener extends ListenerAdapter {

    @Override
    public void onGuildVoiceSelfMute(@NotNull GuildVoiceSelfMuteEvent event) {
        if (checkPermissions(event)) {
            Member member = event.getMember();
            AntiAfkTask.getAfkTimeMap().put(member, new Date().getTime());
            System.out.println("[Klotzscher Pub] add " + member + " to afk map.");
        }
    }

    private boolean checkPermissions(@NotNull GuildVoiceSelfMuteEvent event) {
        for (Role role : event.getMember().getRoles()) {
            if (role.getIdLong() == 628250586806091816L) {
                return false;
            }
        }
        return true;
    }

}
