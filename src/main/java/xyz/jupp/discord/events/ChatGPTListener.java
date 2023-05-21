package xyz.jupp.discord.events;

import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.jetbrains.annotations.NotNull;
import xyz.jupp.discord.commands.handler.Command;
import xyz.jupp.discord.commands.handler.CommandOptions;
import xyz.jupp.discord.core.KlotzscherPubGuild;
import xyz.jupp.discord.utils.EmbedMessageUtil;
import xyz.jupp.discord.utils.SecretKey;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChatGPTListener implements Command {


    private void sendChatGPTRequest(@NotNull SlashCommandInteractionEvent event, @NotNull String input) {
        input = input.replace("%chatgpt", "");
        OpenAiService service = new OpenAiService(SecretKey.chatgptAPIKey);

        final List<ChatMessage> messages = new ArrayList<>();
        final ChatMessage systemMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(), input);
        messages.add(systemMessage);
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
                .builder()
                .model("gpt-3.5-turbo")
                .messages(messages)
                .n(1)
                .maxTokens(100)
                .logitBias(new HashMap<>())
                .build();

        StringBuilder stringBuilder = new StringBuilder();
        service.streamChatCompletion(chatCompletionRequest)
                .doOnError(Throwable::printStackTrace)
                .blockingForEach((answer) -> {
                    for (ChatCompletionChoice choice : answer.getChoices()) {
                        String s = choice.getMessage().getContent();
                        if (s != null) {
                            stringBuilder.append(s);
                        }
                    }
                });
        stringBuilder.append("...");
        service.shutdownExecutor();
        event.replyEmbeds(
                EmbedMessageUtil.buildSlashCommand(stringBuilder.toString(), Color.BLUE, "Diese Nachricht wurde von ChatGPT verfasst.")
        ).setEphemeral(true).queue();
    }

    @Override
    public void action(SlashCommandInteractionEvent event) {
        event.deferReply().queue();
        if (event.getMember().getIdLong() != 213669319358283777L && event.getMember().getIdLong() != 276709802955112448L
                && !event.getMember().getRoles().contains(KlotzscherPubGuild.getGuild().getRoleById(628302155782029332L))
                && !event.getMember().getRoles().contains(KlotzscherPubGuild.getGuild().getRoleById(686689938451726351L))) {
            event.replyEmbeds(
                    EmbedMessageUtil.buildSlashCommand("Das kannst du leider noch nicht benutzen :/", Color.BLUE)
            ).setEphemeral(true).queue();
            return;
        }

        String content = event.getOption("text", OptionMapping::getAsString);
        if (content.length() > 250) {
            event.replyEmbeds(
                    EmbedMessageUtil.buildSlashCommand("Bitte probiere deine Nachricht etwas kürzer zu fassen. Danke", Color.BLUE)
            ).setEphemeral(true).queue();
            return;

        }
        sendChatGPTRequest(event, content);
    }

    @Override
    public CommandOptions getCommandOptions() {
        return new CommandOptions("chatgpt", "Sendet einen Text an ChatGPT (Turbo).");
    }
}
