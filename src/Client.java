import backend.Handler;
import frontend.GUI.App;
import frontend.Controller;

import javax.swing.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    private Socket clientSocket;

    public Client() throws IOException {
        Object[] login = {
                "Server ip address:", new JTextField(),
                "Username: ", new JTextField()
        };
        JOptionPane.showConfirmDialog(null, login, "Login", JOptionPane.DEFAULT_OPTION);

        String server = ((JTextField) login[1]).getText();
        String username = ((JTextField) login[3]).getText();

        try {
            this.clientSocket = new Socket(InetAddress.getByName(server), 1502);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Couldn't connect to server.");
            System.exit(1);
        }

        DataOutputStream os = new DataOutputStream(this.clientSocket.getOutputStream());
        DataInputStream is = new DataInputStream(this.clientSocket.getInputStream());

        Controller controller = new Controller(os);

        new App(controller, username);
        new Handler(is, controller).start();
    }

    public static void main(String[] args) throws IOException {
        new Client();
    }
}
