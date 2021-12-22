package xyz.jupp.discord.tasks;

import net.dv8tion.jda.api.entities.Member;
import xyz.jupp.discord.core.KlotzscherPubGuild;

import java.util.Date;
import java.util.HashMap;
import java.util.Timer;

public class AntiAfkTask implements Tasks {

    private final static HashMap<Member, Long> afkTimeMap = new HashMap<>();

    public static HashMap<Member, Long> getAfkTimeMap() {
        return afkTimeMap;
    }


    @Override
    public void action() {
        getAfkTimeMap().forEach((member, timeLong) -> {
            assert member.getVoiceState().getChannel() != null;
            long timeDifference = (new Date().getTime() - timeLong);
            if (member.getVoiceState().isSelfMuted() && ( timeDifference >= 300000)){
                KlotzscherPubGuild.getGuild().moveVoiceMember(member, KlotzscherPubGuild.getGuild().getAfkChannel()).complete();
                System.out.println("[Klotzscher Pub] moved member " + member.getEffectiveName() + " to afk channel. (" + timeDifference + ")");
            }else if (!member.getVoiceState().isSelfMuted()) {
                afkTimeMap.remove(member);
            }
        });
    }


    @Override
    public Timer getTimer() {
        return new Timer("antiafk");
    }
}
