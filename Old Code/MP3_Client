package com.company;

//need I/O stream and networking libraries
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {

    public void ClientSetup(String ip, String port) {
        try {
            //create connection to server with IP address and TCP Port
            Socket chatSocket = new Socket(ip, Integer.parseInt(port));
            System.out.println("Connection to Server Established");
            //Create input Stream Reader to be able to receive messages from Server
            InputStreamReader stream = new InputStreamReader(chatSocket.getInputStream());

            //create a buffered reader to read messages in stream
            BufferedReader reader = new BufferedReader(stream);

            String message;
            while ((message = reader.readLine()) == null);

            System.out.print("Received Message: ");
            System.out.println(message);

            //System.out.println("Networking Established");
            //create BufferedWriter to write messages to buffer
            //..in order to send messages to server
            PrintWriter writer = new PrintWriter(chatSocket.getOutputStream(), true);

            System.out.println("Sending Messages");
            writer.println("Hey, Group 22 Here" +
                    "!");
        } catch(IOException ex) {
            //used to catch any errors that occur
            ex.printStackTrace();
        }
    }
    public static void main(String[] args) {
        Client Client1 = new Client();
        Client1.ClientSetup(args[0], args[1]);

        Scanner scanner = new Scanner(System.in);
        String connect = scanner.nextLine();

        //continuously check for new messages from server
        //run until user types disconnect
        while(connect != "disconnect") {
            connect = scanner.nextLine();
        }
        System.out.println("Disconnecting...");
    }
}
