package xyz.jupp.discord.events;

import org.jetbrains.annotations.NotNull;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

import java.util.Date;
import java.util.Locale;

class NickNameChangeListenerTest {


    public static void main(String[] args){
        //checkNickname();
        //sortAlgo();

        System.out.println(new Date().getTime());

    }

    // logger
    private final static Logger log = LoggerFactory.getLogger(NickNameChangeListenerTest.class);


    private static void sortAlgo() {

        long[] list = new long[]{12L, 11L, 123L, 6666666666L, 9999999999L};

        long biggest = 0L;
        for (long numberInList : list) {
            if (numberInList > biggest) {
                biggest = numberInList;
            }
        }

        System.out.println(biggest);

    }



    //private static void checkNickname() {
//
    //    String oldName = "Knusperkeks";
    //    String newName = "ficker";
//
    //    if (newName != null){
//
    //        for (String badWord : SecretKey.listOfBadwords){
    //            String cleanNickname = cleanNickname(newName);
    //            System.out.println("badWord = " + badWord);
    //            System.out.println("cleanNickname = " + cleanNickname);
    //            if (cleanNickname.contains(badWord)){
    //                System.out.println( "reset nickname from (" + newName + ") [" + newName + "->" + oldName +" ]");
    //                break;
    //            }
    //        }
//
    //    }
//
    //}


    private static String cleanNickname(@NotNull String rawNickname){
        StringBuilder nickname = new StringBuilder();
        System.out.println("rawNickname: " + rawNickname);
        char[] nicknameArray = rawNickname.toCharArray();
        System.out.println("nicknameArray: " + nicknameArray.toString());
        for (char c : nicknameArray){
            if (Character.isLetter(c)){
                nickname.append(c);
                continue;
            }else if (Character.isDigit(c)){
                String charString = String.valueOf(c);
                int numberAtArray = Integer.parseInt(charString);
                if (numberAtArray == 1){
                    nickname.append(charString.replace("1", "I"));
                }else if (numberAtArray == 3){
                    nickname.append(charString.replace("3", "E"));
                }
            }
        }


        return nickname.toString().toLowerCase(Locale.ROOT);
    }


}