package me.challenges.client;

public class Client {

    private final static String hostname = "192.168.56.1";
    private final static int port = 8950;

    public static void main(String[] args) {

        ClientHandler cHandler = ClientHandler.getClientHandler();
        cHandler.setupClientWorker(hostname, port);

    }





}
