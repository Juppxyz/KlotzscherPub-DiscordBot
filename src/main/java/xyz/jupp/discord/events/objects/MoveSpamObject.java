package xyz.jupp.discord.events.objects;

public class MoveSpamObject {
    private MoveSpamObject() {};

    private long datetimeUTC;
    private int countOfMovement;

    public MoveSpamObject(long datetimeUTC, int countOfMovements){
        this.countOfMovement = countOfMovements;
        this.datetimeUTC = datetimeUTC;
    }


    public void updateCountOfMovement(int newCountOfMovements) {
        countOfMovement += newCountOfMovements;
    }

    public void updateDatetime(long newDatetimeUTC){
        datetimeUTC += newDatetimeUTC;
    }



    // Getter

    public long getDatetimeUTC() {
        return datetimeUTC;
    }

    public int getCountOfMovement() {
        return countOfMovement;
    }
}
