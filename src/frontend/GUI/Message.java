package frontend.GUI;

public class Message {
    public String message;
    public String username;

    public Message(String message, String username) {
        this.message = message;
        this.username = username;
    }

    @Override
    public String toString() {
        return String.format("%s: %s", this.username, this.message);
    }
}
