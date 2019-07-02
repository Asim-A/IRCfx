package me.challenges.server;

import me.challenges.server.ServerWorkers.ProtoServerWorker;
import me.challenges.server.ServerWorkers.ServerWorker;
import me.challenges.server.state.ClientTracker;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class IRCServer {

    private final static int port = 8950;
    public static ClientTracker clientTracker;
    private static IRCServer INSTANCE;
    private ExecutorService executorService;

    public static void main(String[] args) throws UnknownHostException {
        System.out.println("### ON ###");
        System.out.println(InetAddress.getLocalHost());


        IRCServer irc = IRCServer.getInstance();

        irc.handleConnection();
    }

    private IRCServer(){
        initThread();
        clientTracker = ClientTracker.getInstance();
    }

    private void initThread(){
        this.executorService = Executors.newCachedThreadPool();
    }

    private void handleConnection(){
        try(ServerSocket serverSocket = new ServerSocket(port)){

            //noinspection InfiniteLoopStatement
            while(true){
                System.out.println("### ACCEPTING CLIENT CONNECTION ###");
                Socket clientSocket = serverSocket.accept();
                System.out.println(
                        "### (Client) CONNECTED [" +
                         clientSocket.getInetAddress() +
                                ":" +
                                clientSocket.getPort() + "] ");

                /*ServerWorker worker = new ServerWorker(clientSocket);*/
                ProtoServerWorker worker = new ProtoServerWorker(clientSocket);

                executorService.submit(worker);
            }

        } catch (IOException ioe){
            ioe.printStackTrace();
        }

        System.out.println("CONNECTION ENDED");
    }

    public static IRCServer getInstance() {
        if(INSTANCE == null) INSTANCE = new IRCServer();
        return INSTANCE;
    }
}
