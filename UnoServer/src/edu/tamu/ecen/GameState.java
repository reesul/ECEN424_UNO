package edu.tamu.ecen;

import java.util.ArrayList;

public class GameState {

    private static Card nextCard;
    private static boolean gameDirectionForward; //either forward or backward; when a reverse is played, = !direction
    private static int currentPlayer;  // todo, maybe also have player name here?

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

    private static int maxPlayers;

    public static void initGameState(int maxP) {
        nextCard = Util.generateCard();
        currentPlayer = 0;
        maxPlayers = maxP;
        gameDirectionForward = true;
    }

    //increments player count by 1
    public static int incrementPlayers() {
        if(currentPlayer == maxP) {
            currentPlayer = 0;
        }
        else {
            currentPlayer++;
        }

        return currentPlayer;
    }


    public static void updateGameState(Card lastCard, ArrayList<Player> players) {
        //switch case to handle different things since they'll affect other players potentially
        //cases: reverse, skip,
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
        ArrayList<Card> hand = currPlayer.getHand();
        handSize = hand.size();
        for(i = 0; i < handSize; i++) {
            if(hand[i].equals(lastCard)) {
                //remove card from players hand
                list.remove(hand[i]);
                //array = ArrayUtils.removeElement(array, element)
            }
        }

        currPlayer.setHand(hand);

        //case: skip  --> Increment currentPlayer by 2
        if(lastCard.getValue() == S) {
            currentPlayer = incrementPlayers();
            currentPlayer = incrementPlayers();
        }
        //case: Reverse --> change gameDirectionForward variable
        else if(lastCard.getValue() == R) {
            gameDirectionForward = !gameDirectionForward;
            currentPlayer = incrementPlayers();
        }
        //case: Draw two: make next player draw two cards
        else if(lastCard.getValue() == D2) {
            //make next player draw two cards
            //First get next player
            //Then get player's hand
            //then generate a card and add it to that players Hand

            //could be out of bounds if currentPlayer is maxPlayers
            Player p = players.get(currentPlayer = incrementPlayers());
            ArrayList<Card> hand = p.getHand();
            hand.add(generateCard());
            hand.add(generateCard());
            p.setHand(hand);

            currentPlayer = incrementPlayers();
        }
        //case: Draw Four
        else if(lastCard.getValue() == D4) {
            //make next player draw four cards
            //get next player
            //create new hand with size of current players hand + 4
            //set that new hand equal to the players hand
            //add 4 cards to the players hand, then set their hand

            Player p = players.get(currentPlayer = incrementPlayers());
            ArrayList<Card> hand = p.getHand();
            hand.add(generateCard());
            hand.add(generateCard());
            hand.add(generateCard());
            hand.add(generateCard());
            p.setHand(hand);

            currentPlayer = incrementPlayers();
        }
        else if(lastCard.getValue() == W) {
            currentPlayer = incrementPlayers();
        }
        else {
            //normal card played
            currentPlayer = incrementPlayers();
        }

        //Do I need to do anything else here?
        //set nextCard to last card played
        nextCard = lastCard;
    }

    public static String getGameState() {
        //basically a toString method
        String state = "Player " + currentPlayer + "'s turn!";
        state += "\tLast Card Played: " + nextCard.formatCard();

        return state;
    }


}
