package xyz.jupp.discord.commands;

import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import okhttp3.*;
import xyz.jupp.discord.commands.handler.Command;
import xyz.jupp.discord.commands.handler.CommandOptions;
import xyz.jupp.discord.log.LoggerUtil;
import xyz.jupp.discord.utils.EmbedMessageUtil;
import xyz.jupp.discord.utils.SecretKey;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class StartStopMCServerCommand implements Command {

    private static final List<String> permissionRoles = new ArrayList<>(){{
        add("628502738241716244");
        add("628250586806091816");
        add("1254909554949492767");
        add("628301763765862429");
        add("628301849056903178");
        add("628300928574947358");
    }};
    private final static LoggerUtil logger = new LoggerUtil(StartStopMCServerCommand.class.getSimpleName());


    private static boolean sendHetznerRequest(String type) {
        String action = null;
        if (type.equals("start")) {
            action = "poweron";
        } else if (type.equals("stop")) {
            action = "poweroff";
        }

        if (action == null) return false;

        String url = "";//String.format("https://api.hetzner.cloud/v1/servers/%s/actions/%s", SecretKey.minecraftServerIDPaezzle, action);
        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), "{\"type\": \"" + action + "\"}");

        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", "Bearer " + SecretKey.hetznerAPIKey)
                .post(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            int responseCode = response.code();
            String responseBody = response.body().string();
            System.out.println("Status Code: " + responseCode);
            System.out.println("Antwort: " + responseBody);
            return responseCode == 201;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public void action(SlashCommandInteractionEvent event) {
        List<Role> memberRoles = event.getMember().getRoles();

        boolean found = false;
        for (Role role : memberRoles) {
            if (permissionRoles.contains(role.getId())) {
                found = true;
                break;
            }
        }

        if (!found) {
            event.replyEmbeds(EmbedMessageUtil.buildSlashCommand(
                    "Das darfst du leider nicht, frage gerne beim Team oder anderen Nutzern um Hilfe!", Color.ORANGE))
                    .setEphemeral(true).queue();
            return;
        }

        String type = event.getOption("aktion", OptionMapping::getAsString);
        if (type == null || type.isEmpty() || type.isBlank()) {
            event.replyEmbeds(EmbedMessageUtil.buildSlashCommand("Bitte gebe einen Befehl an. (Start/Stop).", Color.GREEN)).setEphemeral(true).queue();
            return;
        }
        boolean isActionExecuted = sendHetznerRequest(type.toLowerCase());
        if (isActionExecuted) {
            event.replyEmbeds(EmbedMessageUtil.buildSlashCommand("Die Aktion wurde erfolgreich ausgeführt..", Color.GREEN)).setEphemeral(true).queue();
        }else {
            event.replyEmbeds(EmbedMessageUtil.buildSlashCommand("Huch, das hat leider nicht geklappt.", Color.RED)).setEphemeral(true).queue();
        }
    }

    @Override
    public CommandOptions getCommandOptions() {
        return new CommandOptions("minecraft", "Startet oder Stoppt den Minecraft-Server.");
    }
}
