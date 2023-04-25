package xyz.jupp.discord.events;

import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import xyz.jupp.discord.commands.handler.Command;
import xyz.jupp.discord.core.KlotzscherPubGuild;
import xyz.jupp.discord.utils.PrivateChannelBuilder;
import xyz.jupp.discord.utils.SecretKey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChatGPTListener implements Command {

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        TextChannel textChannel = event.getChannel().asTextChannel();

        //long plaudernChannel = 628510262659645450L;
        //long botChannel = 796428306228052010L;
        //if ((textChannel.getIdLong() != plaudernChannel) && (textChannel.getIdLong() != botChannel)) {
        //    PrivateChannelBuilder privateChannelBuilder = new PrivateChannelBuilder("Bitte verwende diesen Befehl nur in " + KlotzscherPubGuild.getGuild().getTextChannelById(plaudernChannel).getAsMention() , PrivateChannelBuilder.PrivateChannelType.INFO);
        //    privateChannelBuilder.sendPrivateMessage(event.getAuthor());
        //    return;
        //}

        if (event.getMember().getIdLong() != 213669319358283777L && event.getMember().getIdLong() != 276709802955112448L
                && !event.getMember().getRoles().contains(KlotzscherPubGuild.getGuild().getRoleById(628302155782029332L))
                && !event.getMember().getRoles().contains(KlotzscherPubGuild.getGuild().getRoleById(686689938451726351L))) {
            PrivateChannelBuilder privateChannelBuilder = new PrivateChannelBuilder("Das kannst du leider noch nicht benutzen :/", PrivateChannelBuilder.PrivateChannelType.INFO);
            privateChannelBuilder.sendPrivateMessage(event.getAuthor());
            return;
        }

        if (event.getMessage().getContentRaw().length() > 250) {
            PrivateChannelBuilder privateChannelBuilder = new PrivateChannelBuilder("Bitte probiere deine Nachricht etwas kürzer zu fassen. Danke", PrivateChannelBuilder.PrivateChannelType.INFO);
            privateChannelBuilder.sendPrivateMessage(event.getAuthor());
            return;

        }
        sendChatGPTRequest(event, event.getMessage().getContentRaw());
    }


    private void sendChatGPTRequest(@NotNull MessageReceivedEvent event, @NotNull String input) {
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
        PrivateChannelBuilder privateChannelBuilder = new PrivateChannelBuilder(stringBuilder.toString(), PrivateChannelBuilder.PrivateChannelType.INFO, "Diese Nachricht wurde von ChatGPT verfasst.");
        privateChannelBuilder.sendPrivateMessage(event.getAuthor());
    }

    @Override
    public String getCommand() {
        return "chatgpt";
    }
}
