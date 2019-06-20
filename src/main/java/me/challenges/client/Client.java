package me.challenges.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    private static String hostName = "192.168.56.1";
    private static int port = 8950;
    private static final String[] help = {
            "QUIT - quits the application.",
            "WTS - Changes the Web-Scratcher engine to use pure Java and fetches from worldtimeserveer.",
            "DEFAULT - Changes the Web-Scratcher engine to use JSoup and fetches from Time.is.",
            "RULES: numbers are not allowed."
    };

    public static void main(String[] args) {

        //printWelcomeMessage();
        handleConnection();

    }


    private static void handleConnection(){
        try (
                Socket clientSocket = new Socket(hostName, port);
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {

            String userInput;

            System.out.print("I (Client) [" + InetAddress.getLocalHost()  + ":" + clientSocket.getLocalPort() + "] > ");
            while ((userInput = stdIn.readLine()) != null && !userInput.isEmpty()) {
                //Sender til server

                if("HELP".equalsIgnoreCase(userInput)){
                    System.out.println(returnHelpString());
                    printPrompt(clientSocket);
                    continue;
                }

                out.println(userInput);

                //Mottar input fra socket
                String receivedText = in.readLine();

                if("END".equals(receivedText)){
                    System.out.println("User has terminated the process.");
                    break;
                }

                System.out.println("Server [" + hostName +  ":" + port + "] > \n" + receivedText);
                printPrompt(clientSocket);

            }
        } catch (UnknownHostException e) {
            System.err.println("Unknown host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + hostName);
            System.exit(1);
        }
    }

    private static String returnHelpString(){
        StringBuilder sb = new StringBuilder();

        for(String s : help){
            sb.append(s).append("\n");
        }

        return sb.toString();
    }

    private static void printPrompt(Socket clientSocket){
        System.out.print(
                "[" + clientSocket.getLocalAddress().getHostAddress() + ":" + clientSocket.getLocalPort() + "] > ");
    }

    private static void printWelcomeMessage(){

        System.out.println("Welcome to City Time and Date App");
        System.out.println("Please enter a city or a country.");
        System.out.println("Type help for more information.");

    }

}
