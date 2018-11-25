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

//					System.out.println("Received: " + message);

					String[] message_split = message.split("'", 2);
					String message_beg = message_split[0];
					String[] card_played = message.split("\\*", 2);

					System.out.println(card_played[0]);
					System.out.println(card_played[1]);

					if (Integer.parseInt(message_beg.replaceAll("\\D+","")) == player_number) {
						while (!message.contains("ACK")) {
							Scanner scanner = new Scanner(System.in);

							System.out.println("Enter a card color. If no valid card, enter N:");
							String card = scanner.next().toUpperCase();
							System.out.println("Enter a card value. If no valid card, enter N:");
							String value = scanner.next().toUpperCase();

							String send = card+","+value;
							if (card.contains("W")) {
								System.out.println("You played a wild card; choose the next color");
								String s = "";
								while (!s.equals("R") && !s.equals("B") && !s.equals("G") && !s.equals("Y")) {
									try {
										s = scanner.next().toUpperCase();
										CardValue.valueOf(s);
									} catch (IllegalArgumentException e) {
										System.out.println("please enter a valid color: R, G, B, or Y");
									}
								}

								send += "*"+s;
							}

							PrintWriter writer = new PrintWriter(chatSocket.getOutputStream(),true);
							writer.println(send);
							while ((message = reader.readLine()) == null);

							if (!message.contains("ACK")) {
								System.out.println(message);
							}
							else {
								System.out.println("Card played successfully\n");
								break;
							}
						}

					}

					else {
						System.out.println("Please wait for your turn.\n");

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
