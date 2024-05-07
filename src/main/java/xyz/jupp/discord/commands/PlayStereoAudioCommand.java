package xyz.jupp.discord.commands;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.unions.AudioChannelUnion;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.managers.AudioManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.jupp.discord.audio.AudioPlayerSendHandler;
import xyz.jupp.discord.commands.handler.Command;
import xyz.jupp.discord.commands.handler.CommandOptions;
import xyz.jupp.discord.core.KlotzscherPubGuild;
import xyz.jupp.discord.utils.EmbedMessageUtil;

import java.awt.*;

public class PlayStereoAudioCommand implements Command {

    private static final Logger log = LoggerFactory.getLogger(PlayStereoAudioCommand.class);

    private static boolean isBotConnected = false;

    @Override
    public void action(SlashCommandInteractionEvent event) {
        String audioUrl = event.getOption("url", OptionMapping::getAsString);
        TextChannel textChannel = event.getChannel().asTextChannel();
        Member member = event.getMember();
        GuildVoiceState voiceState = member.getVoiceState();

        if (voiceState == null || !voiceState.inAudioChannel()){
            event.replyEmbeds(
                    EmbedMessageUtil.buildSlashCommand(
                            "Du musst dich in einem VoiceChannel befinden, sonst weiß " +
                                    KlotzscherPubGuild.getGuild().getMemberById(443117714161139712L).getAsMention() +
                                    " nicht wohin :/", Color.ORANGE)
            ).setEphemeral(true).queue();
            return;
        }

        AudioChannelUnion voiceChannel = voiceState.getChannel();
        AudioManager audioManager = KlotzscherPubGuild.getGuild().getAudioManager();
        audioManager.openAudioConnection(voiceChannel);
        log.info("connected to voicechannel (" + voiceChannel.getId() + ")");
        AudioPlayerManager audioPlayerManager = new DefaultAudioPlayerManager();
        AudioPlayer audioPlayer = audioPlayerManager.createPlayer();
        audioManager.setSendingHandler(new AudioPlayerSendHandler(audioPlayer));


        AudioSourceManagers.registerRemoteSources(audioPlayerManager);
        audioPlayerManager.loadItem(audioUrl, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                audioPlayer.playTrack(track);
                System.out.println("Playing: " + track.getInfo().title);
            }

            @Override
            public void loadFailed(FriendlyException throwable) {
                System.out.println("Unable to play: " + audioUrl);
            }

            @Override
            public void noMatches() {
                System.out.println("No matches found for: " + audioUrl);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                AudioTrack firstTrack = playlist.getSelectedTrack();
                if (firstTrack == null) {
                    firstTrack = playlist.getTracks().get(0);
                }
                audioPlayer.playTrack(firstTrack);
                textChannel.sendMessage("Playing: " + firstTrack.getInfo().title).queue();
            }
        });


    }


    @Override
    public CommandOptions getCommandOptions() {
        return new CommandOptions("play", "Spielt eine Audio Datei in Stereo ab.");
    }

}
