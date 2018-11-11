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


    public static void updateGameState(Card lastCard, ArrayList<Player> players) {
        //switch case to handle different things since they'll affect other players potentially
        //cases: reverse, skip,
    }

    public static String getGameState() {
        //basically a toString method
        String state = "Player " + currentPlayer + "'s turn!";
        state += "\tLast Card Played: " + nextCard.formatCard();

        return state;
    }


}
