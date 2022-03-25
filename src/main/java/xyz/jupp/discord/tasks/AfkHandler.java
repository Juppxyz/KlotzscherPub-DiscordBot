package xyz.jupp.discord.tasks;

import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import xyz.jupp.discord.core.KlotzscherPubGuild;
import xyz.jupp.discord.log.LoggerUtil;

import java.util.HashMap;

public class AfkHandler {
    private AfkHandler() {
    }

    private Thread afkThread = new Thread(this::execute);

    private static final LoggerUtil logger = new LoggerUtil(AfkHandler.class.getSimpleName());

    private static final AfkHandler handlerInstance = new AfkHandler();

    public static AfkHandler getHandler() {
        return handlerInstance;
    }


    public void startHandler() {
        if (!afkThread.isAlive()) {
            logger.log("start AfkHandler thread..");
            afkThread.setName("afk-thread");
            afkThread.start();
        }
    }

    // contains all members which are currently in a channel and full muted
    private static final HashMap<Long, Integer> afkMemberCheckMap = new HashMap<>();

    private void execute() {

        while (true) {
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (Member member : KlotzscherPubGuild.getGuild().getMembers()) {
                if(member.getUser().isBot()){
                    return;
                }

                GuildVoiceState voiceState = member.getVoiceState();
                boolean isFullSelfMuted = voiceState.isSelfMuted() && voiceState.isSelfDeafened();
                boolean memberInMap = afkMemberCheckMap.containsKey(member.getIdLong());

                if ((memberInMap && !member.getVoiceState().inAudioChannel())
                        || (memberInMap && voiceState.getChannel().getIdLong() == KlotzscherPubGuild.getGuild().getAfkChannel().getIdLong())) {
                    afkMemberCheckMap.remove(member.getIdLong());
                    continue;
                }

                if (isFullSelfMuted && !memberInMap) {
                    afkMemberCheckMap.put(member.getIdLong(), 1);
                } else if (!isFullSelfMuted && memberInMap) {
                    afkMemberCheckMap.remove(member.getIdLong());
                } else if (isFullSelfMuted && memberInMap) {
                    int value = afkMemberCheckMap.get(member.getIdLong()) + 1;
                    // if higher than 10 move the player
                    if (value >= 15) {

                       /* PrivateChannelBuilder privateChannelBuilder =
                                new PrivateChannelBuilder(
                                        "Jeder brauch mal eine Auszeit.\n Für ein wenig Erholung wurdest du in den Pausenraum gebracht. \n\nBis nachher!",
                                        PrivateChannelBuilder.PrivateChannelType.INFO);
                        privateChannelBuilder.sendPrivateMessage(member.getUser());*/

                        KlotzscherPubGuild.getGuild().moveVoiceMember(member, KlotzscherPubGuild.getGuild().getAfkChannel()).queue();
                        afkMemberCheckMap.remove(member.getIdLong());
                        logger.log("moved to afk channel", member.getId());
                    } else {
                        afkMemberCheckMap.put(member.getIdLong(), value);
                    }
                }
            }
        }

    }


}
