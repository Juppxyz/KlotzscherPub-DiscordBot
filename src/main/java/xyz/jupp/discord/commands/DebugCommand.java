package xyz.jupp.discord.commands;

import net.dv8tion.jda.api.audit.AuditLogEntry;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.restaction.pagination.AuditLogPaginationAction;
import net.dv8tion.jda.api.requests.restaction.pagination.PaginationAction;
import xyz.jupp.discord.commands.handler.Command;
import xyz.jupp.discord.core.KlotzscherPubGuild;

public class DebugCommand implements Command {


    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        AuditLogPaginationAction auditLogEntries = KlotzscherPubGuild.getGuild().retrieveAuditLogs();
        PaginationAction.PaginationIterator<AuditLogEntry> auditIterator = auditLogEntries.iterator();
        Member member = event.getMember();
        System.out.println(auditIterator.next().getUser());
    }




    @Override
    public String getCommand() {
        return "debug";
    }
}
