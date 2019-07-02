package me.challenges.client.ClientWorkers;

import com.google.protobuf.Message;
import me.challenges.server.dataobjects.ChatMessageProto;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.SQLOutput;

public class ProtoClientWorker implements Runnable{

    BufferedReader clientStdIn;
    BufferedOutputStream serverOut;
    BufferedInputStream serverIn;

    private String hostname;
    private int port;

    public ProtoClientWorker(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    @Override
    public void run() {
        handleConnection();
    }

    private void handleConnection(){

        try {
            clientStdIn = new BufferedReader(new InputStreamReader(System.in));

            Socket clientSocket = new Socket(hostname, port);
            serverOut = new BufferedOutputStream(clientSocket.getOutputStream());
            serverIn = new BufferedInputStream(clientSocket.getInputStream());

            String userInput;

            System.out.print("I (Client) [" + InetAddress.getLocalHost()  + ":" + clientSocket.getLocalPort() + "] > ");

            while ((userInput = clientStdIn.readLine()) != null && !userInput.isEmpty()) {

                //OUTGOING
                ChatMessageProto.chatmessage msg = createMessage(userInput);
                msg.writeDelimitedTo(clientSocket.getOutputStream());

                //INCOMING
                ChatMessageProto.chatmessage incomeMsg = ChatMessageProto.chatmessage.parseDelimitedFrom(clientSocket.getInputStream());
                System.out.println("Server [" + hostname +  ":" + port + "] > \n" +
                        "HEADER: " + incomeMsg.getHeader() +
                        "\n" +
                        "BODY: " + incomeMsg.getBody());

                //Prompt
                printPrompt(clientSocket);

            }
            System.out.println("DONE");

            serverOut.close();
            serverIn.close();
            clientStdIn.close();
            clientSocket.close();

        } catch (IOException ioe){
            ioe.printStackTrace();
        }


    }

    private ChatMessageProto.chatmessage createMessage(String message){
        ChatMessageProto.chatmessage.Builder msgBuilder = ChatMessageProto.chatmessage.newBuilder();
        msgBuilder.setHeader("NICK");
        msgBuilder.setBody(message);

        return msgBuilder.build();
    }

    private void printPrompt(Socket clientSocket){
        System.out.print(
                "[" + clientSocket.getLocalAddress().getHostAddress() + ":" + clientSocket.getLocalPort() + "] > ");
    }

}
