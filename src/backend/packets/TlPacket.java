package backend.packets;

import java.util.Arrays;
import java.util.stream.Collectors;

public class TlPacket extends BasePacket {
    public final String[] topics;

    public TlPacket(String ... topics) {
        super((byte) 5, (Arrays.stream(topics)
                               .collect(Collectors.joining("\0")) + "\0\4")
                               .getBytes());
        this.topics = topics;
    }

    public static TlPacket parseRaw(byte[] raw) {
        String[] payload = new String(raw, 0, raw.length - 2).split("\0");
        return new TlPacket(payload);
    }

    @Override
    public String toString() {
        return "TlPacket{" +
                "topics=" + Arrays.toString(topics) +
                '}';
    }
}
