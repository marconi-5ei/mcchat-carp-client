import backend.Handler;
import backend.packets.*;
import frontend.GUI.App;
import frontend.Controller;

import java.io.*;
import java.net.Socket;

public class Client {
    private Socket clientSocket;

    public Client() throws IOException {
        this.clientSocket = new Socket("localhost", 1502);
    }

    public void start() throws IOException, InterruptedException {
        DataOutputStream os = new DataOutputStream(this.clientSocket.getOutputStream());
        DataInputStream is = new DataInputStream(this.clientSocket.getInputStream());

        Controller controller = new Controller(os);

        new App(controller);
        new Handler(is, controller).start();

        Thread.sleep(10);
        new TlrqPacket().send(os);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Client client = new Client();
        client.start();
    }
}
