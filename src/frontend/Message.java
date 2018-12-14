package frontend;

public class Message {
    public String message;
    public String username;

    public Message(String message, String username) {
        this.message = message;
        this.username = username;
    }

    @Override
    public String toString() {
        return message;
    }
}
