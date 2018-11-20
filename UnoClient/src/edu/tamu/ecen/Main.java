package edu.tamu.ecen;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    

    public static void main(String[] args) {
        try {
            //create connection to server with IP address and TCP Port
            Socket chatSocket = new Socket(args[0], Integer.parseInt(args[1]));
            System.out.println("Connection to Server Established");
            //Create input Stream Reader to be able to receive messages from Server
            InputStreamReader stream = new InputStreamReader(chatSocket.getInputStream());
            
            //create a buffered reader to read messages in stream
            BufferedReader reader = new BufferedReader(stream);
            
            int player_number =-1;


            String message = null;

            //get the player number
			while ((message = reader.readLine()) == null);
			player_number = Integer.parseInt(message.replaceAll("\\D+",""));
			System.out.println("You are player " + player_number);

			while (true) {
				while ((message = reader.readLine()) == null); //block until we receive a message from server
				if (message.contains("*")) { //this is the case for messages of during normal gameplay

					System.out.println("Received " + message);

					String[] message_split = message.split("'", 2);
					String message_beg = message_split[0];
					String message_end = message_split[1];
					String[] card_played = message_end.split("\\*", 2);

					System.out.println(card_played[0]);
					System.out.println(card_played[1]);

					if (Integer.parseInt(message_beg.replaceAll("\\D+","")) == player_number) {
						while (!message.contains("ACK")) {
						Scanner scanner = new Scanner(System.in);
						System.out.println("Enter a card color.");
						String card = scanner.next();
						System.out.println("Enter a card value.");
						String value = scanner.next();
						PrintWriter writer = new PrintWriter(chatSocket.getOutputStream(),true);
						writer.println(card+","+value);
						while ((message = reader.readLine()) == null);
						}
					}

					else {
						System.out.println("Please wait for your turn.");

					}
				}
				else if (message.contains("won")) {
					System.out.println(message);
					chatSocket.close();
					return;
				}

			}
		} catch (Exception e) {
        	e.printStackTrace();
		}
	}
} 
