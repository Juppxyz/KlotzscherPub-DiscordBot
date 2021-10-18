package xyz.jupp.discord.events;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import xyz.jupp.discord.core.KlotzscherPubGuild;
import xyz.jupp.discord.log.LoggerUtil;

public class OnGuildJoinListener extends ListenerAdapter {

    // logger
    private final static LoggerUtil logger = new LoggerUtil(OnGuildJoinListener.class.getSimpleName());

    @Override
    public void onGuildJoin(GuildJoinEvent event) {

        for (Member member : KlotzscherPubGuild.getGuild().getMembers()){
            if (member.getRoles().size() == 0){
                event.getGuild().addRoleToMember(member, KlotzscherPubGuild.getGuild().getRoleById(628504141030752256L)).queue();
                logger.log("update role for new member to Guest.", member.getId());
            }


        }

    }
}
