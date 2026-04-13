package xyz.jupp.discord.utils;

import io.github.cdimascio.dotenv.Dotenv;


public class SecretKey {
    static Dotenv dotenv = Dotenv.load();

    // 116.203.156.8
    public final static String connectionString = dotenv.get("CONNECTION_STRING");
    public final static String hetznerAPIKey = dotenv.get("HETZNER_API_KEY");

    public final static String key = dotenv.get("SECRET_KEY");

    public final static String[] listOfBadwords = {"hure", "hore", "whore",
            "nude", "rape", "vergewaltig", "cunt", "fotze", "schwuchtel",
            "schwul", "nigger", "negger", "neger", "nigga", "niggar",
            "neggar", "nigar", "hitler", "hidler", "holocaust"};

    public final static String chatgptAPIKey = dotenv.get("OPENAI_KEY");

}