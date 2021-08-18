package xyz.jupp.discord.events;

import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateNicknameEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.jupp.discord.core.KlotzscherPub;
import xyz.jupp.discord.core.KlotzscherPubGuild;
import xyz.jupp.discord.utils.PrivateChannelBuilder;

import java.util.Locale;

public class NickNameChangeListener extends ListenerAdapter {

    // logger
    private final static Logger log = LoggerFactory.getLogger(NickNameChangeListener.class);

    // list of badwords
    private final static String[] listOfBadwords = {"hure", "hore", "whore", "bitch", "schlampe", "nutte", "nude",
            "rape", "vergewaltig", "cunt", "fotze", "schuwchtel", "schwul", "nigger", "negger", "ficke", "hitler"};

    @Override
    public void onGuildMemberUpdateNickname(@NotNull GuildMemberUpdateNicknameEvent event) {
        String oldName = event.getOldNickname();
        String newName = event.getNewNickname();

        for (String badWord : listOfBadwords){
            if (badWord.equals(cleanNickname(newName))){
                KlotzscherPubGuild.getGuild().modifyNickname(event.getMember(), oldName).queue();

                PrivateChannelBuilder privateChannelBuilder = new PrivateChannelBuilder("❗️Dein Nickname wurde wegen '" + newName + "' zurückgesetzt.", PrivateChannelBuilder.PrivateChannelType.WARN);
                privateChannelBuilder.sendPrivateMessage(event.getUser());

                log.info(KlotzscherPub.getPrefix() + "reset nickname from (" + event.getMember().getId() + ") [" + newName + "->" + oldName +" ]");
            }

        }

    }


    private String cleanNickname(@NotNull String rawNickname){
        StringBuilder nickname = new StringBuilder();
        char[] nicknameArray = rawNickname.toCharArray();

        for (char c : nicknameArray){
            if (Character.isLetter(c)){
                nickname.append(c);
                continue;
            }else if (Character.isDigit(c)){
                String charString = String.valueOf(c);
                int numberAtArray = Integer.parseInt(charString);
                if (numberAtArray == 1){
                    nickname.append(charString.replace("1", "I"));
                }else if (numberAtArray == 3){
                    nickname.append(charString.replace("3", "E"));
                }
            }
        }


        return nickname.toString().toLowerCase(Locale.ROOT);
    }
}
