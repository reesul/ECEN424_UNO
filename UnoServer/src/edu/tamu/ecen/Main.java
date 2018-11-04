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
         * Run the actual game's server here
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
                //todo allow players to send a name when they connection?
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


            while (Util.getWinner(players)==-1) {
                //play a turn
                BlockingQueue<String> curQ=communicationQueue.get(GameState.getCurrentPlayer());
                for (Player p : players) {
                    //send game state info

                    try {
                        BlockingQueue<String> queue = p.getQueue();
                        queue.put(Util.generateMsg(p));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    String response = curQ.take();
                    GameState.updateGameState(new Card(response), players);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

            //found a winner, shut down threads
            for (Player p : players) {
                //inform players of who won
                p.interrupt();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}
