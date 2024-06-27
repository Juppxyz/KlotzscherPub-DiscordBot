package xyz.jupp.discord.events;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import xyz.jupp.discord.core.KlotzscherPub;
import xyz.jupp.discord.core.KlotzscherPubGuild;
import xyz.jupp.discord.log.LoggerUtil;
import xyz.jupp.discord.utils.RssReader;

import java.awt.*;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class OnReadyListener extends ListenerAdapter {

    private final static LoggerUtil logger = new LoggerUtil(OnReadyListener.class.getSimpleName());


    @Override
    public void onReady(ReadyEvent event) {
        if (!checkGuilds()){
            logger.error("the bot can only be used on the KlotzscherPub discord.", null);
            KlotzscherPub.shutdown();
            return;
        }

        logger.log("the bot is only on the KlotzscherPub guild.");
        MembersCountChannelListener.updateMemberCountChannel();
        RegularRoleListener.loadAllMembers();
        startRssFeedTask();
    }


    /** This method ensures that the bot is only on the KlotzscherPub */
    private boolean checkGuilds() {
        List<Guild> listOfGuilds = KlotzscherPub.getJda().getGuilds();
        if (listOfGuilds.size() == 1) {
            Guild guild = listOfGuilds.get(0);
            return guild.getId().equals(KlotzscherPubGuild.getGuild().getId());
        }
        return false;
    }


    private static String rssFeedUrl = "https://www.saechsische.de/rss/dresden";
    private void startRssFeedTask() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            try {
                Elements es = RssReader.getFeedEntries("https://www.saechsische.de/rss/dresden");

                for (Element e : es) {
                    String pubDate = e.select("pubDate").first().text();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);

                    try {
                        ZonedDateTime dateTime = ZonedDateTime.parse(pubDate, formatter);
                        ZonedDateTime now = ZonedDateTime.now();
                        Duration duration = Duration.between(dateTime, now);

                        if (duration.toHours() < 1) {
                            String title = e.select("title").first().text();
                            String description = e.select("description").first().text();
                            String link = e.select("link").first().text();
                            String titlePic = e.select("enclosure").first().attr("url");

                            EmbedBuilder embedBuilder = new EmbedBuilder();
                            embedBuilder.setColor(Color.YELLOW);
                            embedBuilder.setTitle(title);
                            embedBuilder.setDescription(String.format("%s \n [Zum Artikel](%s)", description, link));
                            embedBuilder.setAuthor("Sächsiche Zeitung (https://www.saechsische.de/)");
                            embedBuilder.setThumbnail(titlePic);
                            embedBuilder.setFooter(pubDate);

                            KlotzscherPubGuild.getGuild().getTextChannelById("1248950537215283222").sendMessageEmbeds(embedBuilder.build()).queue();
                        }

                    } catch (Exception ex) {
                        System.err.println("Fehler beim Parsen des Datums: " + pubDate);
                        ex.printStackTrace();
                    }
                }

            } catch (Exception e) {
                logger.error(String.format("Error during getting rss feed from %s", rssFeedUrl), e);
            }
        }, 0, 1, TimeUnit.HOURS);
    }

}
