package backend;

import backend.packets.*;
import frontend.App;
import frontend.Controller;
import frontend.Message;
import frontend.Topic;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client extends Thread {
    private DataOutputStream os;
    private Controller controller;
    private Socket client;

    public Client(String host, Controller controller) throws IOException, InterruptedException {
        this.controller = controller;
        client = new Socket(host, 1502);
        os = new DataOutputStream(client.getOutputStream());
    }

    public Client(Controller controller) throws IOException, InterruptedException {
        this.controller = controller;
        client = new Socket("localhost", 1502);
        os = new DataOutputStream(client.getOutputStream());
    }

    @Override
    public void run() {
        try {
            new Handler(client, controller).start();

            new TlrqPacket().send(os);
            currentThread().sleep(250);

            new SubPacket("dio non e' bello").send(os);
            currentThread().sleep(250);

            new TlrqPacket().send(os);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException, NoSuchMethodException {
        Controller controller = new Controller();

        Client client = new Client(controller);
        client.start();

        new App(controller);
    }
}
