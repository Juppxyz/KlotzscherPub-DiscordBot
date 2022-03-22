package xyz.jupp.discord.events;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateNicknameEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import xyz.jupp.discord.utils.BadNameChecker;

public class NicknameListener extends ListenerAdapter {

    @Override
    public void onGuildMemberUpdateNickname(@NotNull GuildMemberUpdateNicknameEvent event) {
        Member member = event.getMember();
        String newName = event.getNewNickname();
        if (newName == null){return;}
        BadNameChecker.checkName(newName, member, false);
    }

    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        Member member = event.getMember();
        String effectiveName = member.getEffectiveName();
        BadNameChecker.checkName(effectiveName, member, true);
    }



}
