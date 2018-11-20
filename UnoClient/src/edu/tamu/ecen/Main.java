package edu.tamu.ecen.networking;

import java.util.Scanner;

public class Main {
    

    public static void main(String[] args) {
        try {
            //create connection to server with IP address and TCP Port
            Socket chatSocket = new Socket(ip, Integer.parseInt(port));
            System.out.println("Connection to Server Established");
            //Create input Stream Reader to be able to receive messages from Server
            InputStreamReader stream = new InputStreamReader(chatSocket.getInputStream());
            
            //create a buffered reader to read messages in stream
            BufferedReader reader = new BufferedReader(stream);
            
            int player_number;
	  
            String message = null;
	    while ((message = reader.readLine()) == null) {
            	player_number = Integer.parseInt(message.replaceAll("\\D+","");
	    }

	    while (true) {
		while ((message = reader.readLine()) == null) {
			if (message.contains("*") { //this is the case for messages of during normal gameplay 

				String[] message_split = message.split("'", 2);
				String message_beg = message_split[0];
				String message_end = message_split[1];
				String[] card_played = message_end.split("*", 2);

				System.println(card_played[0]);
				System.println(card_played[1]);

				if (Integer.parseInt(message_beg.replaceAll("\\D+","")) == player_number) {
					while (!message.contains("ACK")) {
					Scanner scanner = new Scanner(System.in);
					System.out.println("Enter a card color.");
					String card = scan.next();
					System.out.println("Enter a card value.");
					String value = scan.next();
					PrintWriter writer = new PrintWriter(chatSocket.getOutputStream(),true);
					writer.println(card+","+value);
					while (message = reader.readLine()) == null);
					}
				}
	
				else {
					System.out.println("Please wait for your turn.");
					
				}
			}	
			else if (message.contains("won") {
				System.out.println(message);
				chatSocket.close();
				return;
			}
		}
	}		
            
           }
} 
