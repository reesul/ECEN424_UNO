package edu.tamu.ecen;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Util {

    public static Card generateCard() {
    //TODO generate rand num, use this to choose a card w/ a switch-case block

        return new Card("R","0"); //update this
    }

    public static ArrayList<Card> dealHand() {
        ArrayList<Card> hand = new ArrayList<>(7);

        for (int i = 0; i < 7; i++); {
            hand.add(generateCard());
        }

        return hand;
    }

    public static int getWinner(ArrayList<Player> players) {
        int winner = -1;

        for (int i = 0; i < players.size(); i++) {
            Player p = players.get(i);
            if (p.getHand().size()==0) {
                winner = i;
            }
        }

        return winner;
    }

    public static String generateMsg(Player p) {
        return "***game stat***player state***"; //format
    }



}
