package me.challenges.server;

import me.challenges.server.ServerWorkers.ProtoServerWorker;
import me.challenges.server.dataobjects.ChatMessageProto;
import org.junit.Test;

public class ServerWorkerTest {

    @Test
    public void dummy() {
        ProtoServerWorker s = new ProtoServerWorker();
        ChatMessageProto.chatmessage msg = s.makeMsg();

        System.out.println(msg.getHeader());
        System.out.println(msg.getBody());

        msg.toByteArray();
    }
}