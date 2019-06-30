package me.challenges.server;

import me.challenges.server.dataobjects.ChatMessageProto;

import java.io.*;
import java.net.Socket;

public class ServerWorker implements Runnable{

    private PrintWriter clientSocketWriter;
    private BufferedReader clientSocketReader;

    private PrintWriter recipientSocketWriter;
    private BufferedReader recipientSocketReader;

    private Socket clientSocket;
    private Socket recipientSocket;

    public ServerWorker(Socket socket) {
        clientSocket = socket;
    }

    public ServerWorker(){}

    @Override
    public void run() {
        handleClient(clientSocket);

    }

    public ChatMessageProto.chatmessage makeMsg(){
        ChatMessageProto.chatmessage.Builder message =
                ChatMessageProto.chatmessage.newBuilder();

        message.setHeader("nick");
        message.setBody("Asim-A");

        return message.build();
    }

    private void handleClient(Socket clientSocket) {

        try {
            clientSocketWriter =
                    new PrintWriter(
                            new BufferedWriter(
                                    new OutputStreamWriter(clientSocket.getOutputStream())), true);

            clientSocketReader =
                    new BufferedReader(
                            new InputStreamReader(clientSocket.getInputStream()));

            String line;

            while((line = clientSocketReader.readLine()) != null){
                System.out.println(line);

                if("QUIT".equalsIgnoreCase(line)) {
                    System.out.println("Client: " + clientSocket + " DISCONNECTED");
                    break;
                }
                clientSocketWriter.println(line);
            }
            clientSocketWriter.println("SERVER HAS ENDED CONNECTION");


            closeAllResources();

        } catch(InterruptedIOException ie){
            ie.printStackTrace();
            System.err.println("Interrupted IOException");
        }catch (IOException ioe){
            ioe.printStackTrace();
            System.err.println("IOException");
        }catch(IllegalStateException ise){
            ise.printStackTrace();
            System.err.println("URL is empty, querying numbers is not allowed");
        }

    }

    private void closeAllResources() throws IllegalStateException, IOException{
        closeIOResources();
        closeNetworkResources();
    }

    private void closeNetworkResources() throws IOException{
        clientSocket.close();
        recipientSocket.close();
    }

    private void closeIOResources() throws  InterruptedIOException,IOException{
        clientSocketWriter.close();
        clientSocketReader.close();

        recipientSocketWriter.close();
        recipientSocketReader.close();
    }

    private void establishConnection(String input){

    }

    private void parseMessage(String input, String check){

    }

    public Socket getRecipientSocket() {
        return recipientSocket;
    }

    public void setRecipientSocket(Socket recipientSocket) {
        this.recipientSocket = recipientSocket;
    }
}
