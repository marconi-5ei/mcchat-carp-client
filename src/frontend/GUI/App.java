package frontend.GUI;

import backend.packets.MsgPacket;
import frontend.Controller;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class App {
    public Controller controller;

    public App(Controller controller) {
        this.controller = controller;

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignore) {}

        JFrame jFrame = new JFrame("Carp Chat Client");

        JPanel jPanelContainer = new JPanel(new BorderLayout());
        JPanel jPanelTopics = new JPanel(new BorderLayout());
        JPanel jPanelChat = new JPanel(new BorderLayout());

        JList<Topic> topicJList = new JList<Topic>();
        JList<Message> messageJList = new JList<Message>();
        JTextField inputMessage = new JTextField();

        inputMessage.setEnabled(false);
        messageJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        topicJList.setModel(controller.topicsModel);
        topicJList.addListSelectionListener((e) -> {
            if (!e.getValueIsAdjusting()) {
                if (topicJList.isSelectionEmpty())
                    messageJList.setModel(new DefaultListModel<Message>());
                else
                    messageJList.setModel(topicJList.getSelectedValue().messagesModel);
                inputMessage.setEnabled(!(topicJList.isSelectionEmpty()));
            }
        });

        inputMessage.addActionListener((e) -> {
            String text = inputMessage.getText();
            inputMessage.setText("");
            try {
                new MsgPacket(topicJList.getSelectedValue().topicName, "UserName", text).send(controller.os);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        jPanelTopics.add(topicJList, BorderLayout.WEST);

        jPanelChat.add(messageJList, BorderLayout.PAGE_START);
        jPanelChat.add(inputMessage, BorderLayout.PAGE_END);

        jPanelContainer.add(jPanelTopics, BorderLayout.WEST);
        jPanelContainer.add(jPanelChat, BorderLayout.CENTER);

        jFrame.getContentPane().add(jPanelContainer);

        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(new Dimension(300, 400));
        jFrame.setVisible(true);
    }
}
