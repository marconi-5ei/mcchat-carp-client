package backend;

import backend.packets.InfoPacket;
import backend.packets.MsgPacket;
import backend.packets.TlPacket;
import frontend.Controller;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.security.InvalidParameterException;

public class Handler extends Thread {
    private final boolean DEBUG;

    private final DataInputStream is;
    private final Controller controller;

    public Handler(DataInputStream is, Controller controller) {
        this.is = is;
        this.controller = controller;
        this.DEBUG = true;
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

                        if (DEBUG) System.out.println(msg);
                        try {
                            controller.addMessage(msg);
                        } catch (InvalidParameterException e) {
                            System.err.println(String.format("Topic %s not recognized", msg.topic));
                        }

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

                        if (DEBUG) System.out.println(topics);
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
