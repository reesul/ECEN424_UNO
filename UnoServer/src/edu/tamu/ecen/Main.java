package edu.tamu.ecen;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
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
            final ServerSocket serverSocket = new ServerSocket(6161);


            /* Setup threads for each player */

            //max of ten players
            ArrayList<Player> players = new ArrayList<>(10);
            ArrayList<BlockingQueue<String>> communicationQueue = new ArrayList<>(); //use put and take for unlimited blocking

            // wait until 3 players have joined
            while (players.size() < 3) {

                Socket pSock = serverSocket.accept();
                //todo allow players to send a name when they connection? Probably not
                BlockingQueue<String> queue  = new ArrayBlockingQueue<>(15, true);
                communicationQueue.add(queue);
                players.add(new Player(players.size()+1, pSock, queue));
                players.get(players.size()-1).start();

                System.out.println("Player " + players.size() + " has joined.");
            }

            //once three players have joined, we have enough to play. Allow an extra 15 seconds for more players to join

            try {
                if (players.size() < 10) {
                    serverSocket.setSoTimeout(15000);
                    Socket pSock = serverSocket.accept();

                    BlockingQueue<String> queue  = new ArrayBlockingQueue<>(15, true);
                    players.add(new Player(players.size() + 1, pSock, queue));
                    System.out.println("Player " + players.size() + " has joined.");
                    // start a timer thread to interrupt accept() method after 15 seconds of no connection
                    //Thread timer

                }
            } catch (SocketException e) {
                System.out.println("Game is starting!!");
            }


            //initialize the game state
            GameState.initGameState(players.size());
            for (Player p : players) {
                p.setHand(Util.dealHand());
            }

            //play the game
            int winner = -1;

            while ((winner = Util.getWinner(players))==-1) {
                //play a turn

                for (Player p : players) {
                    //send game state info

                    try {
                        BlockingQueue<String> queue = p.getQueue();
                        queue.put(Util.generateMsg(p));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Player curPlayer = players.get(GameState.getCurrentPlayer());
                BlockingQueue<String> curQ=communicationQueue.get(GameState.getCurrentPlayer());
                try {
                    Card playedCard=null;

                    //Player must play a valid card, if not, ask them again to play a card
                    while (playedCard == null) {
                        try {
                            String response = curQ.take(); //todo reformat if necessary
                            Card card = new Card(response.split(Const.boundary)[1]);

                            if (Util.validCard(curPlayer.getHand(), card, GameState.getNextCard())) {
                                playedCard = card;
                            }
                            else {
                                curQ.put(Const.boundary + "Invalid card played, please try another");
                            }

                        } catch (IllegalArgumentException e) {
                            System.out.println("Current player attempted to play an invalid card");
                            curQ.put(Const.boundary + "Invalid String detected, please use the appropriate card format" + Const.boundary);
                        }

                    }
                    GameState.updateGameState(playedCard, players);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

            //found a winner, shut down threads
            for (Player p : players) {
                    //inform each player who won
                    try {
                        BlockingQueue<String> queue = p.getQueue();
                        queue.put(Const.boundary + "Player " + winner + " has won Uno, thank you for playing!" + Const.boundary);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                p.interrupt();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}
