package xyz.jupp.discord.utils;

import io.github.cdimascio.dotenv.Dotenv;


public class SecretKey {
    private static final Dotenv dotenv = Dotenv.configure()
            .ignoreIfMalformed()
            .ignoreIfMissing()
            .load();

    private static String getOptional(String key) {
        String envValue = System.getenv(key);
        if (envValue != null && !envValue.isBlank()) {
            return envValue;
        }
        String dotenvValue = dotenv.get(key);
        return (dotenvValue == null || dotenvValue.isBlank()) ? null : dotenvValue;
    }

    private static String getRequired(String key) {
        String value = getOptional(key);
        if (value == null) {
            throw new IllegalStateException("Missing required configuration value: " + key
                    + ". Set it as an environment variable or in .env.");
        }
        return value;
    }

    // 116.203.156.8
    public final static String connectionString = getRequired("CONNECTION_STRING");
    public final static String hetznerAPIKey = getOptional("HETZNER_API_KEY");

    public final static String key = getRequired("SECRET_KEY");

    public final static String[] listOfBadwords = {"hure", "hore", "whore",
            "nude", "rape", "vergewaltig", "cunt", "fotze", "schwuchtel",
            "schwul", "nigger", "negger", "neger", "nigga", "niggar",
            "neggar", "nigar", "hitler", "hidler", "holocaust"};

    public final static String chatgptAPIKey = getOptional("OPENAI_KEY");

}
