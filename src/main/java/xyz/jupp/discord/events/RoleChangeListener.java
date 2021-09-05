package xyz.jupp.discord.events;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.jupp.discord.core.KlotzscherPub;
import xyz.jupp.discord.core.KlotzscherPubGuild;

import java.util.List;

public class RoleChangeListener extends ListenerAdapter {

    // logger
    private final static Logger log = LoggerFactory.getLogger(RoleChangeListener.class);


    @Override
    public void onGuildMemberRoleAdd(@NotNull GuildMemberRoleAddEvent event) {
        Member member = event.getMember();
        List<Role> roles = member.getRoles();
        long regularRoleID = 628302155782029332L;

        for (Role role : roles) {
            if (role.getIdLong() == regularRoleID){
                log.info(KlotzscherPub.getPrefix() + "remove regular role from " + member.getId());
                KlotzscherPubGuild.getGuild().removeRoleFromMember(member.getIdLong(), KlotzscherPubGuild.getGuild().getRoleById(regularRoleID)).queue();
                break;
            }
        }


    }


}
