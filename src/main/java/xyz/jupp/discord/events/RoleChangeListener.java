package xyz.jupp.discord.events;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import xyz.jupp.discord.core.KlotzscherPub;
import xyz.jupp.discord.core.KlotzscherPubGuild;
import xyz.jupp.discord.database.RegularCollection;
import xyz.jupp.discord.log.LoggerUtil;

import java.util.List;

public class RoleChangeListener extends ListenerAdapter {

    // logger
    private final static LoggerUtil log = new LoggerUtil(RoleChangeListener.class.getSimpleName());


    @Override
    public void onGuildMemberRoleAdd(@NotNull GuildMemberRoleAddEvent event) {
        Member member = event.getMember();
        List<Role> roles = member.getRoles();
        long regularRoleID = 628302155782029332L;

        RegularCollection regularCollection = new RegularCollection(member);

        if (!(regularCollection.getActiveTime() >= KlotzscherPub.getNeededTimeForRegularRole())) {

            for (Role role : roles) {
                if (role.getIdLong() == regularRoleID) {
                    log.log("remove regular role ", member.getId());
                    KlotzscherPubGuild.getGuild().removeRoleFromMember(member.getIdLong(), KlotzscherPubGuild.getGuild().getRoleById(regularRoleID)).queue();
                    break;
                }
            }
        }


    }


}
