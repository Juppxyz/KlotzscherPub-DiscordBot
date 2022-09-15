package xyz.jupp.discord.events;

import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import xyz.jupp.discord.core.KlotzscherPubGuild;
import xyz.jupp.discord.log.LoggerUtil;

public class OnGuildJoinListener extends ListenerAdapter {

    // logger
    private final static LoggerUtil logger = new LoggerUtil(OnGuildJoinListener.class.getSimpleName());

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        event.getGuild().addRoleToMember(event.getMember(), KlotzscherPubGuild.getGuild().getRoleById(628504141030752256L)).complete();
        logger.log("update role for new member to Guest", event.getMember().getId());
    }


}
