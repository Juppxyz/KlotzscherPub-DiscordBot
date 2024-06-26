package xyz.jupp.discord.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import xyz.jupp.discord.log.LoggerUtil;

import java.io.IOException;

public class RssReader {

    private final static LoggerUtil logger = new LoggerUtil(RssReader.class.getSimpleName());
    
    public static Elements getFeedEntries(String url) {
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            logger.error(String.format("Error duringp fetching data from url %s", url), e);
        }

        if (doc != null) {
            return doc.select("item");
        } else {
            return new Elements();
        }
    }


}
