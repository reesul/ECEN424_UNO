package edu.tamu.ecen;

import java.util.ArrayList;

public class Util {


    public static Card generateCard() {
    //TODO generate rand num, use this to choose a card w/ a switch-case block

        return new Card("R","N0"); //update this
    }

    public static ArrayList<Card> dealHand() {
        ArrayList<Card> hand = new ArrayList<>(7);

        for (int i = 0; i < 7; i++) {
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
         String gameState = GameState.getGameState();


         StringBuilder handBuilder = new StringBuilder();

         for (Card c : p.getHand()) {
             handBuilder.append(c.formatCard() + "  ");
         }

        String hand = handBuilder.toString();

        //return Const.boundary + Const.delimiter + gameState + Const.delimiter + hand + Const.delimiter; //TODO any formatting changes
        return Const.boundary + gameState + Const.delimiter + hand + Const.boundary;

    }

    /*
    Checks if a played card is actually in the players hand
     */
    public static boolean validCard(ArrayList<Card> hand, Card card, Card lastCard) {
        for (Card c : hand) {
            if (c.equals(card)) {
                return true;
            }
        }

        //todo check if the card they played is valid compared to the last one
        //TODO Kyle, please do this part

        return false;
    }

}
