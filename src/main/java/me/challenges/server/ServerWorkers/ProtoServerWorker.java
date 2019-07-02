package me.challenges.server.ServerWorkers;

import me.challenges.server.dataobjects.ChatMessageProto;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class ProtoServerWorker implements Runnable{

    private BufferedOutputStream clientOut;
    private BufferedInputStream clientIn;

    private BufferedOutputStream reciepientOut;
    private BufferedInputStream reciepientIn;

    private Socket clientSocket;
    private Socket recipientSocket;

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
            clientOut = new BufferedOutputStream(clientSocket.getOutputStream());
            clientIn = new BufferedInputStream(clientSocket.getInputStream());

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

                if(myMsg != null){
                    myMsg.writeDelimitedTo(clientSocket.getOutputStream());
                }


            }

            System.out.println("WORKER CANCELLED");

            clientIn.close();
            clientOut.close();
            clientSocket.close();


        } catch (SocketException so){
            System.err.println("CLIENT CANCELED");
        } catch(IOException | InterruptedException ioe){
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
