package xyz.jupp.discord.utils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class PrivacyProtectionUtil {

    // ArrayList with users which are protected
    private static final ArrayList<String> privacyProtectedUser = new ArrayList<>(){{
        add("310044636402024449");
    }};


    // check if member is protected
    public static boolean isMemberProtected(@NotNull String id) {
        return privacyProtectedUser.contains(id);
    }

}
