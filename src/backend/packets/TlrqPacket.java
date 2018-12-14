package backend.packets;

public class TlrqPacket extends BasePacket {
    public TlrqPacket() {
        super((byte) 4, new byte[] {});
    }

    public static TlrqPacket parseRaw() {
        return new TlrqPacket();
    }
}
