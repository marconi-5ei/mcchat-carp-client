package backend.packets;

import java.util.Arrays;

public class UnsubPacket extends BasePacket {
    public final String topic;

    public UnsubPacket(String topic) {
        super((byte) 2, (topic + "\0").getBytes());
        this.topic = topic;
    }

    public static UnsubPacket parseRaw(byte[] raw) {
        return new UnsubPacket(new String(Arrays.copyOfRange(raw, 0, raw.length - 1)));
    }
}
