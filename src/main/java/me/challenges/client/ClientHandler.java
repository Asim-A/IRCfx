package me.challenges.client;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientHandler {

    private static ClientHandler INSTANCE;
    private ExecutorService executor;

    private ClientHandler() {
        executor = Executors.newSingleThreadExecutor();
    }

    public void setupClientWorker(String hostname, int port){
        executor.submit(new ClientWorker(hostname, port));
    }

    public static ClientHandler getClientHandler(){
        if(INSTANCE == null) INSTANCE = new ClientHandler();
        return INSTANCE;
    }

}
