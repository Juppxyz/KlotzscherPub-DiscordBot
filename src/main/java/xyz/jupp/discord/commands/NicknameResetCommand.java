package xyz.jupp.discord.commands;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.jupp.discord.commands.handler.Command;
import xyz.jupp.discord.utils.Message;

public class NicknameResetCommand implements Command {

    private final static Logger log = LoggerFactory.getLogger(NicknameResetCommand.class);

    @Override
    public void action(String[] args, GuildMessageReceivedEvent event) {
        log.info("execute nickname command...");
        if (permissionCheck(event)){
            Member member = event.getMember();
            if (member != null){
                Message.send(event.getChannel(), "name: " + event.getAuthor().getName());
            }
        }
    }

    @Override
    public String getCommand() {
        return "nickname";
    }
}
