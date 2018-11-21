package edu.tamu.ecen;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class GameState {

    private static Card nextCard;
    private static boolean gameDirectionForward; //either forward or backward; when a reverse is played, = !direction
    private static int currentPlayer;  // todo, maybe also have player name here?
    private static int maxPlayers;


    public static Card getNextCard() {
        return nextCard;
    }

    public static void setNextCard(Card nextCard) {
        GameState.nextCard = nextCard;
    }

    public static boolean isGameDirectionForward() {
        return gameDirectionForward;
    }

    public static void setGameDirectionForward(boolean gameDirectionForward) {
        GameState.gameDirectionForward = gameDirectionForward;
    }

    public static int getCurrentPlayer() {
        return currentPlayer;
    }

    public static void setCurrentPlayer(int currentPlayer) {
        GameState.currentPlayer = currentPlayer;
    }

    public static int getMaxPlayers() {
        return maxPlayers;
    }

    public static void setMaxPlayers(int maxPlayers) {
        GameState.maxPlayers = maxPlayers;
    }



    public static void initGameState(int maxP) {
        Card newCard = Util.generateCard();
        //ensure we do not start with a wild card
        while (newCard.getColor() == CardColor.W) {
            newCard = Util.generateCard();
        }


        nextCard = newCard;
        currentPlayer = 0;
        maxPlayers = maxP;
        gameDirectionForward = true;
    }

    //increments player count by 1
    public static int incrementPlayers() {
        int gameDir = gameDirectionForward ? 1: -1;
        currentPlayer = Math.floorMod((currentPlayer + gameDir), maxPlayers); // use modulus to keep between 0 and maxPlayers-1

//        if(currentPlayer == maxPlayers) {
//            currentPlayer = 0;
//        }
//        else {
//            currentPlayer++;
//        }


        System.out.println("Incremented players " + currentPlayer + gameDirectionForward);
        return currentPlayer;
    }


    public static void updateGameState(Card lastCard, ArrayList<Player> players, CardColor wildCard) {
        //switch case to handle different things since they'll affect other players potentially
        //cases: reverse, skip,

        //todo if wildCard is not null, then set the nextCard to something that has the requested color
        if (wildCard != null) { //todo handle this better, does not appear to work correctly; next color not updated correctly
            nextCard = new Card(wildCard.toString(), "W");
        }

        //Direction of the game
        //If draw 2 is played, make player draw 2 cards
            //Get players cards, add 2, increment turn

        //To Remove card from players hand:
        //get currentPlayer
        //get currentPlayer's hand
        //check which of their cards matches with last card  played (equals checks if one card is equal to another)
        //replace that card with a new card (generate card)
        //set players hand
        int handSize = 0;
        int i = 0;

        Player currPlayer = players.get(currentPlayer);

        int next = incrementPlayers();
        Player nextPlayer = players.get(next);

        ArrayList<Card> hand = currPlayer.getHand();

        handSize = hand.size();
        for(i = 0; i < handSize; i++) {

            if(hand.get(i).equals(lastCard)) {
                //remove card from players hand
                hand.remove(hand.get(i));
                break;
                //array = ArrayUtils.removeElement(array, element)
            }
        }

        currPlayer.setHand(hand);

        //case: skip  --> Increment currentPlayer by 2
        if(lastCard.getValue() == CardValue.S) {
            next = incrementPlayers();
        }
        //case: Reverse --> change gameDirectionForward variable
        else if(lastCard.getValue() == CardValue.R) {
            gameDirectionForward = !gameDirectionForward;
            next = incrementPlayers(); // undo moving in previous direction
            next = incrementPlayers();
        }
        //case: Draw two: make next player draw two cards
        else if(lastCard.getValue() == CardValue.D2) {
            //make next player draw two cards
            //First get next player
            //Then get player's hand
            //then generate a card and add it to that players Hand
            ArrayList<Card> nextHand = nextPlayer.getHand();

            //could be out of bounds if currentPlayer is maxPlayers
            nextHand.add(Util.generateCard());
            nextHand.add(Util.generateCard());
            nextPlayer.setHand(nextHand);

            next = incrementPlayers();
        }
        //case: Draw Four
        else if(lastCard.getValue() == CardValue.D4) {
            //make next player draw four cards
            //get next player
            //create new hand with size of current players hand + 4
            //set that new hand equal to the players hand
            //add 4 cards to the players hand, then set their hand
            ArrayList<Card> nextHand = nextPlayer.getHand();

            //could be out of bounds if currentPlayer is maxPlayers
            nextHand.add(Util.generateCard());
            nextHand.add(Util.generateCard());
            nextHand.add(Util.generateCard());
            nextHand.add(Util.generateCard());
            nextPlayer.setHand(nextHand);

            next = incrementPlayers();
        }
        else if(lastCard.getColor() == CardColor.W) {
            lastCard = new Card(wildCard.toString(), "W"); //todo check better
        }
        else {
            //normal card played
        }

        //Do I need to do anything else here?
        //set nextCard to last card played
        currentPlayer = next;
        nextCard = lastCard;
    }

    public static String getGameState() {
        //basically a toString method
        String state = "Player " + (currentPlayer+1) + "'s turn!";
        state += "\tLast Card Played: " + nextCard.formatCard();

        return state;
    }


}
