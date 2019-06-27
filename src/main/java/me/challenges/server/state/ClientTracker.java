package me.challenges.server.state;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ClientTracker {

    public static ClientTracker INSTANCE;
    Map<String, Socket> clients;


    private ClientTracker(){
        clients = new HashMap<>();
    }

    public static ClientTracker getInstance(){
        if(INSTANCE == null) INSTANCE = new ClientTracker();

        return INSTANCE;
    }

}
