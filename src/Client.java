import backend.Handler;
import frontend.GUI.App;
import frontend.Controller;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    private Socket clientSocket;

    public Client(String server, String username) throws IOException {
        this.clientSocket = new Socket(InetAddress.getByName(server), 1502);
        DataOutputStream os = new DataOutputStream(this.clientSocket.getOutputStream());
        DataInputStream is = new DataInputStream(this.clientSocket.getInputStream());

        Controller controller = new Controller(os);

        new App(controller, username);
        new Handler(is, controller).start();
    }

    public static void main(String[] args) throws IOException {
        if (args.length < 2 || args[0].equals("help")) {
            System.out.println("Run java -jar mcchat-carp-client.jar <server-ip> <username>");
        } else {
            new Client(args[0], args[1]);
        }
    }
}
