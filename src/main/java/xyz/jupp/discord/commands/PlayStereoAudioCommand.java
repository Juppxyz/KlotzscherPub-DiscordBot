package xyz.jupp.discord.commands;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.playback.AudioFrame;
import net.dv8tion.jda.api.audio.AudioSendHandler;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.unions.AudioChannelUnion;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.jupp.discord.commands.handler.Command;
import xyz.jupp.discord.commands.handler.CommandOptions;
import xyz.jupp.discord.core.KlotzscherPubGuild;
import xyz.jupp.discord.utils.EmbedMessageUtil;

import java.awt.*;
import java.nio.ByteBuffer;

public class PlayStereoAudioCommand implements Command {

    private static final Logger log = LoggerFactory.getLogger(PlayStereoAudioCommand.class);

    private static boolean isBotConnected = false;

    @Override
    public void action(SlashCommandInteractionEvent event) {
        String audioUrl = event.getOption("url", OptionMapping::getAsString);
        TextChannel textChannel = event.getChannel().asTextChannel();

        Member member = event.getMember();
        GuildVoiceState voiceState = member.getVoiceState();
        if (!voiceState.inAudioChannel()){
            event.replyEmbeds(
                    EmbedMessageUtil.buildSlashCommand(
                            "Du musst dich in einem VoiceChannel befinden, sonst weiß " +
                                    KlotzscherPubGuild.getGuild().getMemberById(443117714161139712L).getAsMention() +
                                    " nicht wohin :/", Color.ORANGE)
            ).setEphemeral(true).queue();
            return;
        }

        AudioChannelUnion voiceChannel = member.getVoiceState().getChannel();
        log.info("connect to voicechannel (" + voiceChannel.getId() + ")");
        //KlotzscherPubGuild.getGuild().getAudioManager().openAudioConnection(member.getVoiceState().getChannel());


        event.replyEmbeds(
                EmbedMessageUtil.buildSlashCommand("⏱️ Deine aktive Zeit beträgt: ", Color.BLUE)
        ).setEphemeral(true).queue();
    }
    @Override
    public CommandOptions getCommandOptions() {
        return new CommandOptions("play", "Spielt eine Audio Datei in Stereo ab.");
    }


    public class AudioPlayerSendHandler implements AudioSendHandler {
        private final AudioPlayer audioPlayer;
        private AudioFrame lastFrame;

        public AudioPlayerSendHandler(AudioPlayer audioPlayer) {
            this.audioPlayer = audioPlayer;
        }

        @Override
        public boolean canProvide() {
            lastFrame = audioPlayer.provide();
            return lastFrame != null;
        }

        @Override
        public ByteBuffer provide20MsAudio() {
            return ByteBuffer.wrap(lastFrame.getData());
        }

        @Override
        public boolean isOpus() {
            return true;
        }
    }

}
