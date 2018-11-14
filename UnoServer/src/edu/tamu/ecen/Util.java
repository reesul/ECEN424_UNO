package edu.tamu.ecen;

import java.util.ArrayList;
import java.util.Random;

public class Util {


    public static Card generateCard() {
    //TODO generate rand num, use this to choose a card w/ a switch-case block
        Random rand = new Random();
        Card card = new Card();
        //rand.nextInt(maximum) + minimum
        int randomNumber = rand.nextInt(108) + 1;

        //108 cards in an uno Deck, so 108 cases
        //UNO Rules: http://play-k.kaserver5.org/Uno.html
        switch randomNumber {
            case 1: return new card("R","0");   break;
            case 2: return new card("G", "0");  break;
            case 3: return new card("Y", "0");  break
            case 4: return new card("B", "0");  break;
            case 5:
            case 6: return new card("R", "1");  break;
            case 7:
            case 8: return new card("G", "1");  break;
            case 9:
            case 10: return new card("Y", "1");  break;
            case 11:
            case 12: return new card("B","1");   break;
            case 13:
            case 14: return new card("R", "2");  break;
            case 15:
            case 16: return new card("G", "2");  break;
            case 17:
            case 18: return new card("Y", "2");  break;
            case 19:
            case 20: return new card("B", "2");  break;
            case 21:
            case 22: return new card("R", "3");  break;
            case 23:
            case 24: return new card("G", "3");  break;
            case 25:
            case 26: return new card("Y", "3");  break;
            case 27:
            case 28: return new card("B", "3");  break;
            case 29:
            case 30: return new card("R", "4");  break;
            case 31:
            case 32: return new card("G", "4");  break;
            case 33:
            case 34: return new card("Y", "4");  break;
            case 35:
            case 36: return new card("B", "4");  break;
            case 37:
            case 38: return new card("R", "5");  break;
            case 39:
            case 40: return new card("G", "5");  break;
            case 41:
            case 42: return new card("Y", "5");  break;
            case 43:
            case 44: return new card("B", "5");  break;
            case 45:
            case 46: return new card("R", "6");  break;
            case 47:
            case 48: return new card("G", "6");  break;
            case 49:
            case 50: return new card("Y", "6");  break;
            case 51:
            case 52: return new card("B", "6");  break;
            case 53:
            case 54: return new card("R", "7");  break;
            case 55:
            case 56: return new card("G", "7");  break;
            case 57:
            case 58: return new card("Y", "7");  break;
            case 59:
            case 60: return new card("B", "7");  break;
            case 61:
            case 62: return new card("R", "8");  break;
            case 63:
            case 64: return new card("G", "8");  break;
            case 65:
            case 66: return new card("Y", "8");  break;
            case 67:
            case 68: return new card("B", "8");  break;
            case 69:
            case 70: return new card("R", "9");  break;
            case 71:
            case 72: return new card("G", "9");  break;
            case 73:
            case 74: return new card("Y", "9");  break;
            case 75:
            case 76: return new card("B", "9");  break;

            case 77:
            case 78: return new card("R", "D2"); break;
            case 79:
            case 80: return new card("G", "D2"); break;
            case 81:
            case 82: return new card("Y", "D2"); break;
            case 83:
            case 84: return new card("B", "D2"); break;
            case 85:
            case 86: return new card("R", "S");  break;
            case 87:
            case 88: return new card("G", "S");  break;
            case 89:
            case 90: return new card("Y", "S");  break;
            case 91:
            case 92: return new card("B", "S");  break;
            case 93:
            case 94: return new card("R", "R");  break;
            case 95:
            case 96: return new card("G", "R");  break;
            case 97:
            case 98: return new card("Y", "R"); break;
            case 99:
            case 100: return new card("B", "R"); break;

            case 101:
            case 102:
            case 103:
            case 104: return new card("W", "W"); break;
            case 105:
            case 106:
            case 107:
            case 108: return new card("W","D4"); break;
        }
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
         String gameState = GameState.getGameState();
         String hand = "";

         for (Card c : p.getHand()) {
             hand += c.formatCard() + "  ";
         }

        //return Const.boundary + Const.delimiter + gameState + Const.delimiter + hand + Const.delimiter; //TODO any formatting changes
        return Const.boundary + gameState + Const.delimiter + hand + Const.boundary;

    }

    /*
    Checks if a played card is actually in the players hand
     */
    //Compare players hand, game state, and played card to check for valid card
    //validCard(List of cards in players hand, Card they played, Last card played (Card on top))
    public static boolean validCard(ArrayList<Card> hand, Card card, Card lastCard) {
        /*  To check if a card is valid: Card must be:
                (A) an actual card in the deck
                (B) in the player's hand
                (C) Must be same color or number as Top card in play
                           except for Wild Cards, which can be played on top of anything
         */
        //(A) Actual card in deck

        //(B) in player's hand
        for (Card c : hand) {
            if (c.equals(card)) {
                return true;
            }
        }

        //(C) same color or number
        if((card.getColor()) == (lastCard.getColor())) {
            return true;
        }
        if((card.getValue()) == (lastCard.getValue())) {
            return true;
        }
        //wild card
        if((Card.getValue()) == W) {
            return true;
        }

        //This accounts for if its not an actual card in the deck
        return false;
    }

}
