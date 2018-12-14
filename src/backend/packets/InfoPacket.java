package backend.packets;

import java.io.DataOutputStream;

public class InfoPacket extends BasePacket {
    public final byte version;

    public InfoPacket(byte version) {
        super((byte) 0, new byte[] { version });
        this.version = version;
    }

    public static InfoPacket parseRaw(byte[] raw) {
        return new InfoPacket(raw[0]);
    }
}
