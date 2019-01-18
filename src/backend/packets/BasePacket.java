package backend.packets;

import java.io.DataOutputStream;
import java.io.IOException;

public abstract class BasePacket {
    public final byte opcode;
    public final byte[] raw;

    public BasePacket(byte opcode, byte[] raw) {
        this.opcode = opcode;
        this.raw = raw;
    }

    public void send(DataOutputStream os) {
        try {
            os.write(opcode);
            os.write(raw);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
