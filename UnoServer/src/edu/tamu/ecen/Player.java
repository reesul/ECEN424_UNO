package edu.tamu.ecen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

public class Player extends Thread {

    private int playerNum;
    private Socket sock;
    private String playerName;
    private ArrayList<Card> hand;
    private BlockingQueue<String> queue;


    public BlockingQueue<String> getQueue() {
        return queue;
    }

    public void setQueue(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    public int getPlayerNum() {
        return playerNum;
    }

    public void setPlayerNum(int playerNum) {
        this.playerNum = playerNum;
    }

    public Socket getSock() {
        return sock;
    }

    public void setSock(Socket sock) {
        this.sock = sock;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }


    public Player(int playerNum, Socket sock, BlockingQueue<String> queue) {
        this.playerNum = playerNum;
        this.sock = sock;
        this.queue = queue;
        playerName = "Player " + playerNum;
    }

    public Player(int playerNum, Socket sock, String name, BlockingQueue<String> queue) {
        this.playerNum = playerNum;
        this.sock = sock;
        this.queue = queue;
        playerName = name;
    }

    @Override
    public void run() {
        System.out.println("started thread for "+ playerName);
        PrintWriter writer; InputStreamReader stream; BufferedReader reader;
        try {
            writer = new PrintWriter(sock.getOutputStream(), true);
            stream = new InputStreamReader(sock.getInputStream());
            reader = new BufferedReader(stream);

        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        //todo make sure this doesn't die
        try {
            while (true) {
                System.out.println("waiting to take from q");
                String send = queue.take();
                System.out.println("Sending: " + send);
                //send state
                writer.println(send);

                //if current player, then we also need to wait for their response
                if (GameState.getCurrentPlayer()+1 == playerNum && !send.contains("ACK")) {
                    System.out.println(playerName + "'s thread awaiting response");
                    String response = null;
                    try {
                        while ((response = reader.readLine()) == null) ; //block until we get a response
                        System.out.println("Player responded with " + response);
                        queue.put(response);


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


            }
        }  catch (InterruptedException e) {
            System.out.println(playerName = "'s thread shutting down...");
        }  catch (Exception e) {
            e.printStackTrace();
        }

    }

}
