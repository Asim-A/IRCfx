package me.challenges.server.state;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ClientTracker {

    private static ClientTracker INSTANCE;
    private Map<String, Socket> clients;


    private ClientTracker(){
        clients = new HashMap<>();
    }

    public static ClientTracker getInstance(){
        if(INSTANCE == null) INSTANCE = new ClientTracker();

        return INSTANCE;
    }

    public boolean putClient(String nick, Socket client){
        if(!(inMap(nick))){
            clients.put(nick, client);
            return true;
        }
        return false;
    }

    public Map<String, Socket> getClients() {
        return clients;
    }

    public boolean inMap(String nick){
        return clients.containsKey(nick);
    }
}
