package me.challenges.server.ServerWorkers;

import me.challenges.server.dataobjects.ChatMessageProto;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class ProtoServerWorker implements Runnable{

    private Socket clientSocket;

    public ProtoServerWorker(){}

    public ProtoServerWorker(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        handleConnection();
    }

    private void handleConnection(){

        try {

            ChatMessageProto.chatmessage myMsg;

            System.out.println("MESSAGES INCOMING");

            while (true) {
                Thread.sleep(1);

                myMsg = ChatMessageProto.chatmessage.parseDelimitedFrom(clientSocket.getInputStream());

                System.out.println("HEADER: " + myMsg.getHeader());
                System.out.println("BODY: " + myMsg.getBody());

                if(myMsg.getBody().equalsIgnoreCase("QUIT")){
                    break;
                }

                myMsg.writeDelimitedTo(clientSocket.getOutputStream());


            }

            System.out.println("WORKER CANCELLED");

            clientSocket.close();


        } catch (SocketException so){
            System.err.println("CLIENT CANCELED");
        } catch(InterruptedException | IOException ioe){
            ioe.printStackTrace();
        }

        System.out.println("DONE WITH CLIENT");

    }

    public ChatMessageProto.chatmessage makeMsg(){
        ChatMessageProto.chatmessage.Builder message =
                ChatMessageProto.chatmessage.newBuilder();

        message.setHeader("nick");
        message.setBody("Asim-A");

        return message.build();
    }
}
