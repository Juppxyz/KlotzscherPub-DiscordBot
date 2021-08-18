package xyz.jupp.discord.events;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.jupp.discord.core.KlotzscherPub;
import xyz.jupp.discord.core.KlotzscherPubGuild;

public class OnGuildJoinListener extends ListenerAdapter {

    // logger
    private final static Logger log = LoggerFactory.getLogger(OnGuildJoinListener.class);

    @Override
    public void onGuildJoin(@NotNull GuildJoinEvent event) {

        for (Member member : KlotzscherPubGuild.getGuild().getMembers()){
            if (member.getRoles().size() == 0){
                event.getGuild().addRoleToMember(member, KlotzscherPubGuild.getGuild().getRoleById(628504141030752256L));
                log.info(KlotzscherPub.getPrefix() + "update role for new member (" + member.getId() + ") to Gast.");
            }


        }

    }
}
