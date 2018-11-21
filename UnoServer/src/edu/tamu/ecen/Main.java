package edu.tamu.ecen;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {

    public static void main(String[] args) {

        /**
         * Do basic testing here!!!
         */

        boolean t = false;
        if (t) return;

        /**
         * End basic testing
         */


        /**
         * Run the actual game's server from here on
         */
        try {
            System.out.println("Beginning Lobbying Period");
            final ServerSocket serverSocket = new ServerSocket(6161);


            /* Setup threads for each player */

            //max of ten players
            ArrayList<Player> players = new ArrayList<>(10);
            ArrayList<BlockingQueue<String>> communicationQueue = new ArrayList<>(); //use put and take for unlimited blocking
            BlockingQueue<String> recQueue = new ArrayBlockingQueue<>(15, true);


            // wait until 3 players have joined
            while (players.size() < 3) {

                Socket pSock = serverSocket.accept();
                //todo allow players to send a name when they connection? Probably not, harder to check for

                addNewPlayer(pSock, players, communicationQueue, recQueue);

            }

            //once three players have joined, we have enough to play. Allow an extra 15 seconds for more players to join
            System.out.println("Three players have joined; we have enough to play\nBut let's wait 15 seconds to see if more connect!");


            try {
                while (players.size() < 10) {
                    serverSocket.setSoTimeout(1000); //todo make this 15 seconds again
                    Socket pSock = serverSocket.accept();

                    addNewPlayer(pSock, players, communicationQueue, recQueue);

                }
            } catch (SocketTimeoutException e) {
                System.out.println("Game is starting!!");
            }


            //initialize the game state
            GameState.initGameState(players.size());

            //Deal each of the players 7 random cards
            for (Player p : players) {
                p.setHand(Util.dealHand());
            }

            //play the game
            int winner = -1;
            int turn = 0;

            while ((winner = Util.getWinner(players))==-1) {
                //play a turn
                System.out.println("Turn " + (++turn)+"\n");

                for (Player p : players) {
                    //send game state info

                    try {
                        BlockingQueue<String> sendQueue = p.getrQueue();
                        sendQueue.put(Util.generateMsg(p));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Player curPlayer = players.get(GameState.getCurrentPlayer());
                BlockingQueue<String> sendQueue=communicationQueue.get(GameState.getCurrentPlayer());
                try {
                    Card playedCard=null;
                    CardColor wildColor = null;


                    //Player must play a valid card, if not, ask them again to play a card
                    while (playedCard == null) {
                        System.out.println("Waiting input from player " + GameState.getCurrentPlayer());
                        try {
                            String response = recQueue.take(); //todo reformat if necessary
                            System.out.println("Player responded to main with " + response);

                            if (response.contains("N")) { //give the player a another card
                                curPlayer.getHand().add(Util.generateCard());
                                playedCard = GameState.getNextCard();
                                break;
                            }

                            if (response.contains("W") && response.contains("*")) {
                                wildColor = CardColor.valueOf(response.split("\\*")[1]);
                                response = response.split("\\*")[0];
                            }

                            Card card = new Card(response);

                            //Compare players hand, game state, and played card to check for valid card
                            if (Util.validCard(curPlayer.getHand(), card, GameState.getNextCard())) {
                                playedCard = card;
                                System.out.println("Played valid card");
                            }
                            else {
                                sendQueue.put("That card is not in your hand or is not valid for this turn");
//                                Thread.sleep(100); //sleep so the other thread has a chance to take from the Q
                            }

                        } catch (IllegalArgumentException e) {
                            System.out.println("Current player attempted to play an invalid card");
                            sendQueue.put("Invalid String detected, please use the appropriate card format");
//                            Thread.sleep(100); //sleep so the other thread has a chance to take from the Q
                        }

                    }
                    //handle end of successfully played card
                    System.out.println("Sending acknowledgment");
                    sendQueue.put("ACK");
//                  Thread.sleep(100); //sleep so the other thread has a chance to take from the Q

//                    if (playedCard.getColorStr().contains("W")) {
//                        //TODO parse for extra color
//                        wildColor = CardColor.valueOf()
//                    }

                    if (!playedCard.toString().contains("N")) { //if a player had to draw, don't apply the last card
                        GameState.updateGameState(playedCard, players, wildColor); //comment out if rule requiring player to draw until they can play
                    } else {
                        GameState.incrementPlayers();
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

            //found a winner, shut down threads
            for (Player p : players) {
                    //inform each player who won
                    try {
                        BlockingQueue<String> queue = p.getrQueue();
                        queue.put("Player " + winner + " has won Uno on the " + turn + "th turn, thank you for playing!");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                //End player threads
                p.interrupt();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    private static void addNewPlayer(Socket sock, ArrayList<Player> players, ArrayList<BlockingQueue<String>> communicationQueue, BlockingQueue<String> tQueue) {
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(15, true);
        communicationQueue.add(queue);

        players.add(new Player(players.size()+1, sock, queue, tQueue));
        Player curPlayer = players.get(players.size()-1);
        curPlayer.start();

        try {   //let player know who they are so they know when to play
            System.out.println("Welcome player " + players.size());
            queue.put("Welcome to UNO! You are Player " + (players.size()));

        } catch (InterruptedException e) { e.printStackTrace(); }

        System.out.println("Player " + players.size() + " has joined.");
    }

}
