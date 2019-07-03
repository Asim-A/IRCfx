package me.challenges.server;

import me.challenges.server.state.ClientTracker;

import java.net.Socket;

public class CommandParser {

    public static void checkNick(String header, String body, Socket clientSocket){
        if(header.equalsIgnoreCase("N")){
            ClientTracker.getInstance().putClient(body, clientSocket);
        }
    }

}
