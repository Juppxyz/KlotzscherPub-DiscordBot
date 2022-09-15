package xyz.jupp.discord.events;

import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import xyz.jupp.discord.core.KlotzscherPubGuild;
import xyz.jupp.discord.database.MemberCountCollection;
import xyz.jupp.discord.log.LoggerUtil;

public class MembersCountChannelListener extends ListenerAdapter {

    // channel name:
    // » Mitglieder: ZAHL

    private static final LoggerUtil logger = new LoggerUtil(MembersCountChannelListener.class.getSimpleName());

    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        updateMemberCountChannel();
    }

    @Override
    public void onGuildMemberRemove(@NotNull GuildMemberRemoveEvent event) {
        updateMemberCountChannel();
    }

    public static void updateMemberCountChannel() {
        long amountOfMembers = KlotzscherPubGuild.getGuild().getMemberCount();
        MemberCountCollection memberCountCollection = new MemberCountCollection();
        String placeholder = "» Mitglieder: " + amountOfMembers;
        logger.log("updated name for members-count channel", String.valueOf(amountOfMembers));
        memberCountCollection.saveGuildMemberCount();
        KlotzscherPubGuild.getMemberCountChannel().getManager().setName(placeholder).queue();
    }

}
