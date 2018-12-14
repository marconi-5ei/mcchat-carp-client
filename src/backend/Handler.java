package backend;

import backend.packets.InfoPacket;
import backend.packets.MsgPacket;
import backend.packets.SubPacket;
import backend.packets.TlPacket;
import frontend.Controller;
import frontend.Topic;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class Handler extends Thread {
    private final DataInputStream is;
    private final Controller controller;

    public Handler(Socket client, Controller controller) throws IOException {
        this.is = new DataInputStream(client.getInputStream());
        this.controller = controller;
    }

    @Override
    public void run() {
        while (true) {
            try {

                byte opcode = is.readByte();

                switch (opcode) {
                    case 0:
                        InfoPacket info = InfoPacket.parseRaw(new byte[] {opcode, is.readByte()});
                        System.out.println("Protocol version: " + info.version);
                        break;

                    case 3:
                        ByteArrayOutputStream msgBuffer = new ByteArrayOutputStream();
                        byte msgb;
                        for (int j = 0; j < 3; ++j) {
                            do {
                                msgb = is.readByte();
                                msgBuffer.write((int) msgb);
                            } while (msgb != '\0');
                        }

                        byte[] msgPayload = msgBuffer.toByteArray();
                        MsgPacket msg = MsgPacket.parseRaw(msgPayload);

                        System.out.println(msg);
                        controller.addMessage(msg);
                        break;

                    case 5:
                        ByteArrayOutputStream tlBuffer = new ByteArrayOutputStream();
                        byte tlb;
                        do {
                            tlb = is.readByte();
                            tlBuffer.write((int) tlb);
                        } while (tlb != '\4');

                        byte[] tlPayload = tlBuffer.toByteArray();
                        TlPacket topics = TlPacket.parseRaw(tlPayload);

                        System.out.println(topics);
                        controller.addTopics(topics);

                        break;

                    default:
                        System.err.println("Unrecognized opcode: " + opcode);
                        break;
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
