package edu.tamu.ecen;

import java.util.ArrayList;
import java.util.Random;

public class Util {


    public static Card generateCard() {
    //TODO generate rand num, use this to choose a card w/ a switch-case block
        Random rand = new Random();
        //rand.nextInt(maximum) + minimum
        int randomNumber = rand.nextInt(108) + 1;

        //108 cards in an uno Deck, so 108 cases
        //UNO Rules: http://play-k.kaserver5.org/Uno.html
        switch (randomNumber) {
            case 1:
                return new Card("R", "N0");
            case 2:
                return new Card("G", "N0");
            case 3:
                return new Card("Y", "N0");
            case 4:
                return new Card("B", "N0");
            case 5:
            case 6:
                return new Card("R", "N1");
            case 7:
            case 8:
                return new Card("G", "N1");
            case 9:
            case 10:
                return new Card("Y", "N1");
            case 11:
            case 12:
                return new Card("B", "N1");
            case 13:
            case 14:
                return new Card("R", "N2");
            case 15:
            case 16:
                return new Card("G", "N2");
            case 17:
            case 18:
                return new Card("Y", "N2");
            case 19:
            case 20:
                return new Card("B", "N2");
            case 21:
            case 22:
                return new Card("R", "N3");
            case 23:
            case 24:
                return new Card("G", "N3");
            case 25:
            case 26:
                return new Card("Y", "N3");
            case 27:
            case 28:
                return new Card("B", "N3");
            case 29:
            case 30:
                return new Card("R", "N4");
            case 31:
            case 32:
                return new Card("G", "N4");
            case 33:
            case 34:
                return new Card("Y", "N4");
            case 35:
            case 36:
                return new Card("B", "N4");
            case 37:
            case 38:
                return new Card("R", "N5");
            case 39:
            case 40:
                return new Card("G", "N5");
            case 41:
            case 42:
                return new Card("Y", "N5");
            case 43:
            case 44:
                return new Card("B", "N5");
            case 45:
            case 46:
                return new Card("R", "N6");
            case 47:
            case 48:
                return new Card("G", "N6");
            case 49:
            case 50:
                return new Card("Y", "N6");
            case 51:
            case 52:
                return new Card("B", "N6");
            case 53:
            case 54:
                return new Card("R", "N7");
            case 55:
            case 56:
                return new Card("G", "N7");
            case 57:
            case 58:
                return new Card("Y", "N7");
            case 59:
            case 60:
                return new Card("B", "N7");
            case 61:
            case 62:
                return new Card("R", "N8");
            case 63:
            case 64:
                return new Card("G", "N8");
            case 65:
            case 66:
                return new Card("Y", "N8");
            case 67:
            case 68:
                return new Card("B", "N8");
            case 69:
            case 70:
                return new Card("R", "N9");
            case 71:
            case 72:
                return new Card("G", "N9");
            case 73:
            case 74:
                return new Card("Y", "N9");
            case 75:
            case 76:
                return new Card("B", "N9");
            case 77:
            case 78:
                return new Card("R", "D2");
            case 79:
            case 80:
                return new Card("G", "D2");
            case 81:
            case 82:
                return new Card("Y", "D2");
            case 83:
            case 84:
                return new Card("B", "D2");
            case 85:
            case 86:
                return new Card("R", "S");
            case 87:
            case 88:
                return new Card("G", "S");
            case 89:
            case 90:
                return new Card("Y", "S");
            case 91:
            case 92:
                return new Card("B", "S");
            case 93:
            case 94:
                return new Card("R", "R");
            case 95:
            case 96:
                return new Card("G", "R");
            case 97:
            case 98:
                return new Card("Y", "R");
            case 99:
            case 100:
                return new Card("B", "R");

            case 101:
            case 102:
            case 103:
            case 104:
                return new Card("W", "W");
            case 105:
            case 106:
            case 107:
            case 108:
                return new Card("W","D4");
            default:
                System.out.println("***ERROR, hit default case when generating card");
                return new Card("R", "N0");
        }
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
        return gameState + Const.delimiter + hand;

    }

    /*
    Checks if a played card is actually in the players hand
     */
    //Compare players hand, game state, and played card to check for valid card
    //validCard(List of cards in players hand, Card they played, Last card played (Card on top))
    public static boolean validCard(ArrayList<Card> hand, Card card, Card lastCard) {
        /*  To check if a card is valid: Card must be:
                (B) in the player's hand
                (C) Must be same color or number as Top card in play
                           except for Wild Cards, which can be played on top of anything
         */
        //(B) in player's hand

        System.out.println("Checking for valid card:: last: " + lastCard.toString() + " and played: " + card.toString());

        boolean isInHand = false;
        for (Card c : hand) {
            if (c.equals(card)) {
                isInHand = true;
            }
        }
        if (!isInHand) return false;


        //(C) same color or number
        if((card.getColor()) == (lastCard.getColor())) {
            return true;
        }
        if((card.getValue()) == (lastCard.getValue())) {
            return true;
        }
        //wild card
        if((card.getColor()) == CardColor.W) {
            return true;
        }

        //This accounts for if its not an actual card in the deck
        return false;
    }

}
