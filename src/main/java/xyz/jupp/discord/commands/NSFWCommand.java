package xyz.jupp.discord.commands;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import xyz.jupp.discord.commands.handler.Command;
import xyz.jupp.discord.core.KlotzscherPubGuild;
import xyz.jupp.discord.log.LoggerUtil;
import xyz.jupp.discord.utils.PrivateChannelBuilder;

public class NSFWCommand implements Command {

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        Member member = event.getMember();
        Role nsfwRole = KlotzscherPubGuild.getGuild().getRoleById(925430805944221726L);

        PrivateChannelBuilder privateChannelBuilder = null;
        if (!member.getRoles().contains(nsfwRole)){
            privateChannelBuilder = new PrivateChannelBuilder("Du hast nun Zugriff auf den NSFW Kanal. Viel Spaß ;)", PrivateChannelBuilder.PrivateChannelType.INFO);
            privateChannelBuilder.sendPrivateMessage(event.getAuthor());
            KlotzscherPubGuild.getGuild().addRoleToMember(member.getIdLong(), nsfwRole).queue();
            new LoggerUtil(NSFWCommand.class.getSimpleName()).log("add nsfw role", member.getId());
        }else {
            privateChannelBuilder = new PrivateChannelBuilder("Du hast bereits Zugriff auf den NSFW Kanal.", PrivateChannelBuilder.PrivateChannelType.INFO);
            privateChannelBuilder.sendPrivateMessage(event.getAuthor());
        }


    }

    @Override
    public String getCommand() {
        return "gibsmir";
    }

}
