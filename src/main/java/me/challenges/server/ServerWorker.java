package me.challenges.server;

import java.io.*;
import java.net.Socket;

public class ServerWorker implements Runnable{

    private final Socket clientSocket;
    private Socket recipientSocket;

    public ServerWorker(Socket socket) {
        clientSocket = socket;
    }

    @Override
    public void run() {
        handleClient(clientSocket);
    }

    private void handleClient(Socket clientSocket) {

        try(PrintWriter outStream =
                    new PrintWriter(
                            new BufferedWriter(
                                    new OutputStreamWriter(clientSocket.getOutputStream())), true);
            BufferedReader reader =
                    new BufferedReader(
                            new InputStreamReader(clientSocket.getInputStream()))
        ) {
            String line;

            while((line = reader.readLine()) != null){
                System.out.println(line);

                if("QUIT".equalsIgnoreCase(line)) {
                    System.out.println("Client: " + clientSocket + " DISCONNECTED");
                    break;
                }
                outStream.println(line);
            }
            outStream.println("SERVER HAS ENDED CONNECTION");
            clientSocket.close();

        }catch(InterruptedIOException ie){
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

    private void establishConnection(String input){

    }

    private void parseMessage(){

    }

    public Socket getRecipientSocket() {
        return recipientSocket;
    }

    public void setRecipientSocket(Socket recipientSocket) {
        this.recipientSocket = recipientSocket;
    }
}
