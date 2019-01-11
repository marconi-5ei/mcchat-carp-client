import backend.Handler;
import frontend.GUI.App;
import frontend.Controller;

import java.io.*;
import java.net.Socket;

public class Client {
    private Socket clientSocket;

    public Client() throws IOException {
        this.clientSocket = new Socket("localhost", 1502);
    }

    public void start(String username) throws IOException {
        DataOutputStream os = new DataOutputStream(this.clientSocket.getOutputStream());
        DataInputStream is = new DataInputStream(this.clientSocket.getInputStream());

        Controller controller = new Controller(os);

        new App(controller, username);
        new Handler(is, controller).start();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        if (args.length == 0 || args[0].equals("help")) {
            System.out.println("Run java -jar mcchat-carp-client.jar <username>");
        } else {
            Client client = new Client();
            client.start(args[0]);
        }
    }
}
