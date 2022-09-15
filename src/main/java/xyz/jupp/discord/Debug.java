package xyz.jupp.discord;

import java.util.Date;

public class Debug {

    public static void main(String[] args) {
        try {
            long timeStart = new Date().getTime();
            Thread.sleep(1000 * 10);
            long timeEnd = new Date().getTime();
            System.out.println("Ergebnis: " + (timeEnd - timeStart));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Aktuelle Uhrzeit: " + new Date().getTime());
    }

}
