package xyz.jupp.discord.tasks;

import java.util.Timer;
import java.util.TimerTask;

public interface Tasks {

    void action();
    Timer getTimer();


    default void startTask(int delay) {
        getTimer().schedule(getTimerTask(), delay);
    }

    default TimerTask getTimerTask() {
        return new TimerTask() {
            public void run() {
                action();
            }
        };
    }





}
