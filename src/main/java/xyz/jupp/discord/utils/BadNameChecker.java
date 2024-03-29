package xyz.jupp.discord.utils;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import org.jetbrains.annotations.NotNull;
import xyz.jupp.discord.core.KlotzscherPubGuild;
import xyz.jupp.discord.events.NicknameListener;
import xyz.jupp.discord.log.LoggerUtil;

import java.awt.*;
import java.util.Locale;

public class BadNameChecker {

    // logger
    private final static LoggerUtil logger = new LoggerUtil(NicknameListener.class.getSimpleName());

    public static void checkName(@NotNull String nickName, @NotNull Member member, boolean kick) {
        String effectiveName = member.getEffectiveName();

        String cleanNickname = cleanNickname(nickName);
        for (String badWord : SecretKey.listOfBadwords) {
            if (cleanNickname.contains(badWord)) {
                KlotzscherPubGuild.getGuild().modifyNickname(member, effectiveName).queue();
                String content = (kick ? "❗️Um den Pub betreten zu können, benötigst du einen angemessenen Namen!" : "❗️ Dein Nickname wurde wegen '" + badWord + "' zurückgesetzt." );

                EmbedBuilder embedBuilder = new EmbedBuilder();
                embedBuilder.setTitle("KlotzscherPub Bot");
                embedBuilder.setColor(Color.RED);
                embedBuilder.setDescription(content);
                member.getUser().openPrivateChannel().complete().sendMessageEmbeds(embedBuilder.build()).queue();
                if (kick) {
                    member.kick("forbidden nickname").queue();
                }

                logger.log("reset nickname [" + effectiveName + " -> " + nickName + "]", member.getId());
                break;
            }
        }

    }


    private static String cleanNickname(@NotNull String rawNickname) {
        String cleanNickname =
                rawNickname.replaceAll("1", "i").replaceAll("3", "e").replaceAll(" ", "");

        return cleanNickname.toLowerCase(Locale.ROOT);
    }

}
