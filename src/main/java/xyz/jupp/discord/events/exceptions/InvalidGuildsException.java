package xyz.jupp.discord.events.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.jupp.discord.core.KlotzscherPub;

public class InvalidGuildsException extends Exception{

    private static Logger log = LoggerFactory.getLogger(InvalidGuildsException.class);

    public InvalidGuildsException(){
        log.error("invalid guild(s) found! ");
        KlotzscherPub.shutdown();
    }

}
