package backend.packets;

public class MsgPacket extends BasePacket {
    public final String topic;
    public final String username;
    public final String message;

    public MsgPacket(String topic, String username, String message) {
        super((byte) 3, (topic + "\0" + username + "\0" + message + "\0").getBytes());
        this.topic = topic;
        this.username = username;
        this.message = message;
    }

    public static MsgPacket parseRaw(byte[] raw) {
        String[] payload = new String(raw, 0, raw.length - 1).split("\0");
        if (payload.length < 3)
            return new MsgPacket(payload[0], payload[1], "");
        else
            return new MsgPacket(payload[0], payload[1], payload[2]);
    }

    @Override
    public String toString() {
        return "MsgPacket{" +
                "topic='" + topic + '\'' +
                ", username='" + username + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
