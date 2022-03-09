package xyz.jupp.discord.events;

import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateNicknameEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import xyz.jupp.discord.core.KlotzscherPubGuild;
import xyz.jupp.discord.log.LoggerUtil;
import xyz.jupp.discord.utils.PrivateChannelBuilder;
import xyz.jupp.discord.utils.SecretKey;

import javax.annotation.Nonnull;
import java.util.Locale;

public class NicknameChangeListener extends ListenerAdapter {

    // logger
    private final static LoggerUtil logger = new LoggerUtil(NicknameChangeListener.class.getSimpleName());


    @Override
    public void onGuildMemberUpdateNickname(@Nonnull GuildMemberUpdateNicknameEvent event) {
        String oldName = event.getOldNickname();
        String newName = event.getNewNickname();

        if (newName != null){
            if (oldName == null){
                oldName = event.getMember().getEffectiveName();
            }


            for (String badWord : SecretKey.listOfBadwords){
                String cleanNickname = cleanNickname(newName);
                if (cleanNickname.contains(badWord)){
                    KlotzscherPubGuild.getGuild().modifyNickname(event.getMember(), oldName).complete();

                    PrivateChannelBuilder privateChannelBuilder = new PrivateChannelBuilder("❗️ Dein Nickname wurde wegen '" + newName + "' zurückgesetzt.", PrivateChannelBuilder.PrivateChannelType.ERROR);
                    privateChannelBuilder.sendPrivateMessage(event.getUser());

                    logger.log("reset nickname [" + oldName + " -> " + newName + "]", event.getMember().getId());
                    break;
                }
            }

        }



    }


    private String cleanNickname(@NotNull String rawNickname){
        String cleanNickname = rawNickname.replaceAll("1", "i").replaceAll("3", "e");

        /*
        StringBuilder nickname = new StringBuilder();
        char[] nicknameArray = rawNickname.toCharArray();

        for (char c : nicknameArray){
            if (Character.isLetter(c)){
                nickname.append(c);
            }else if (Character.isDigit(c)){
                String charString = String.valueOf(c);
                int numberAtArray = Integer.parseInt(charString);
                if (numberAtArray == 1){
                    nickname.append(charString.replace("1", "I"));
                }else if (numberAtArray == 3){
                    nickname.append(charString.replace("3", "E"));
                }
            }
        }*/

        return cleanNickname.toLowerCase(Locale.ROOT);
    }
}
