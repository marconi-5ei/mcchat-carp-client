package backend.packets;

import java.nio.charset.Charset;
import java.util.Arrays;

public class SubPacket extends BasePacket {
    public final String topic;

    public SubPacket(String topic) {
        super((byte) 1, (topic + "\0").getBytes());
        this.topic = topic;
    }

    public static SubPacket parseRaw(byte[] raw) {
        return new SubPacket(new String(Arrays.copyOfRange(raw, 0, raw.length - 1, Charset.forName("UTF-8"))));
    }
}
