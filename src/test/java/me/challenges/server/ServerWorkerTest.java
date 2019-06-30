package me.challenges.server;

import me.challenges.server.dataobjects.ChatMessageProto;
import org.junit.Test;

import static org.junit.Assert.*;

public class ServerWorkerTest {

    @Test

    public void dummy() {
        ServerWorker s = new ServerWorker();
        ChatMessageProto.chatmessage msg = s.makeMsg();

        System.out.println(msg.getHeader());
        System.out.println(msg.getBody());
        System.out.println(msg.getHeaderBytes());
        System.out.println(msg.getBodyBytes());
    }
}