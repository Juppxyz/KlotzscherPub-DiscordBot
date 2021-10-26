package xyz.jupp.discord.events;

import net.dv8tion.jda.api.audit.AuditLogEntry;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.restaction.pagination.AuditLogPaginationAction;
import net.dv8tion.jda.api.requests.restaction.pagination.PaginationAction;
import org.jetbrains.annotations.NotNull;
import xyz.jupp.discord.core.KlotzscherPubGuild;
import xyz.jupp.discord.events.objects.MoveSpamObject;

import java.util.HashMap;

public class MoveListener extends ListenerAdapter {

    private final static HashMap<Long, MoveSpamObject> spamMovementList = new HashMap<>();

    @Override
    public void onGuildVoiceMove(@NotNull GuildVoiceMoveEvent event) {

        AuditLogPaginationAction auditLogEntries = KlotzscherPubGuild.getGuild().retrieveAuditLogs();
        PaginationAction.PaginationIterator<AuditLogEntry> auditIterator = auditLogEntries.iterator();


        //Member member = event.getMember();

        System.out.println(auditIterator.next().getUser());



        /*
        new Thread(() -> {
        }).start();
        */


    }

    private boolean checkIfMemberCanMoveUnlimited(@NotNull Member member){
        for (Role role : member.getRoles()){

            // Manager 628301763765862429L
            // Admin 628250586806091816L
            if (role.getIdLong() == 628301763765862429L || role.getIdLong() == 628250586806091816L){
                return true;
            }
        }
        return false;
    }


    private boolean isSomeUserSpammingMoveAction() {



        return false;
    }

}
