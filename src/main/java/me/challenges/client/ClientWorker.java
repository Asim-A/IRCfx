package me.challenges.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientWorker implements Runnable{

    private String hostname;
    private int port;

    public ClientWorker(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    @Override
    public void run() {
        handleConnection();
    }

    private void handleConnection(){
        try (
                Socket clientSocket = new Socket(hostname, port);
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {

            String userInput;

            System.out.print("I (Client) [" + InetAddress.getLocalHost()  + ":" + clientSocket.getLocalPort() + "] > ");
            while ((userInput = stdIn.readLine()) != null && !userInput.isEmpty()) {
                //Sender til server

                if("HELP".equalsIgnoreCase(userInput)){
                    printPrompt(clientSocket);
                    continue;
                }

                out.println(userInput);

                //Mottar input fra socket
                String receivedText = in.readLine();

                if("END".equalsIgnoreCase(receivedText)){
                    System.out.println("User has terminated the process.");
                    break;
                }

                System.out.println("Server [" + hostname +  ":" + port + "] > \n" + receivedText);
                printPrompt(clientSocket);

            }

        } catch (UnknownHostException e) {
            System.err.println("Unknown host " + hostname);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + hostname);
            System.exit(1);
        }
    }



    private void printPrompt(Socket clientSocket){
        System.out.print(
                "[" + clientSocket.getLocalAddress().getHostAddress() + ":" + clientSocket.getLocalPort() + "] > ");
    }
}
