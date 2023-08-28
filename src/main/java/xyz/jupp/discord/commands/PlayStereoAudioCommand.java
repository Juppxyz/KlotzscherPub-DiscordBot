package xyz.jupp.discord.commands;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
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
        if (!audioManager.isConnected()) {
            event.replyEmbeds(
                    EmbedMessageUtil.buildSlashCommand("Ein unerwarteter Fehler ist aufgetreten. Sry :/", Color.ORANGE)
            ).setEphemeral(true).queue();
            return;
        }
        log.info("connected to voicechannel (" + voiceChannel.getId() + ")");

        AudioPlayerManager audioPlayerManager = new DefaultAudioPlayerManager();
        AudioPlayer audioPlayer = audioPlayerManager.createPlayer();

        audioManager.setSendingHandler(new AudioPlayerSendHandler(audioPlayer));



    }


    @Override
    public CommandOptions getCommandOptions() {
        return new CommandOptions("play", "Spielt eine Audio Datei in Stereo ab.");
    }

}
