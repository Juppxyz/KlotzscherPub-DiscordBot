package xyz.jupp.discord.events;

import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import xyz.jupp.discord.commands.handler.Command;
import xyz.jupp.discord.core.KlotzscherPubGuild;
import xyz.jupp.discord.utils.PrivateChannelBuilder;
import xyz.jupp.discord.utils.SecretKey;

import java.util.List;

public class ChatGPTListener implements Command {

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        TextChannel textChannel = event.getChannel().asTextChannel();

        long plaudernChannel = 628510262659645450L;
        if (textChannel.getIdLong() != plaudernChannel) {
            PrivateChannelBuilder privateChannelBuilder = new PrivateChannelBuilder("Bitte verwende diesen Befehl nur in " + KlotzscherPubGuild.getGuild().getTextChannelById(plaudernChannel).getAsMention() , PrivateChannelBuilder.PrivateChannelType.INFO);
            privateChannelBuilder.sendPrivateMessage(event.getAuthor());
            return;
        }

        if (event.getMember().getIdLong() != 213669319358283777L) {
            PrivateChannelBuilder privateChannelBuilder = new PrivateChannelBuilder("Das kannst du leider noch nicht benutzen :/", PrivateChannelBuilder.PrivateChannelType.INFO);
            privateChannelBuilder.sendPrivateMessage(event.getAuthor());
            return;
        }

        if (event.getMessage().getContentRaw().length() > 1200) {
            PrivateChannelBuilder privateChannelBuilder = new PrivateChannelBuilder("Bitte probiere deine Nachricht etwas kürzer zu fassen. Danke", PrivateChannelBuilder.PrivateChannelType.INFO);
            privateChannelBuilder.sendPrivateMessage(event.getAuthor());
            return;

        }
        sendChatGPTRequest(event, event.getMessage().getContentRaw());
    }

    private void sendChatGPTRequest(@NotNull MessageReceivedEvent event, @NotNull String input) {
        input = input.replace("%chatgpt", "");
        OpenAiService service = new OpenAiService(SecretKey.chatgptAPIKey);
        CompletionRequest completionRequest = CompletionRequest.builder().prompt(input).model("gpt-3.5-turbo").echo(true).build();
        List<CompletionChoice> choices = service.createCompletion(completionRequest).getChoices();
        for (CompletionChoice choice : choices) {
            event.getChannel().sendMessage(choice.getText()).queue();
        }
    }

    @Override
    public String getCommand() {
        return "chatgpt";
    }
}
