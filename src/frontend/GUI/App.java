package frontend.GUI;

import backend.packets.MsgPacket;
import backend.packets.SubPacket;
import backend.packets.TlrqPacket;
import frontend.Controller;

import javax.swing.*;
import java.awt.*;

public class App {
    public Controller controller;
    public String username;

    public App(Controller controller, String username) {
        this.controller = controller;
        this.username = username;

        new TlrqPacket().send(controller.os);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignore) {}

        JFrame jFrame = new JFrame("Carp Chat Client");

        JPanel jPanelContainer = new JPanel(new BorderLayout());
        JPanel jPanelTopics = new JPanel(new BorderLayout());
        JPanel jPanelChat = new JPanel(new BorderLayout());

        JList<Topic> topicJList = new JList<Topic>();
        JList<Message> messageJList = new JList<Message>();
        JTextField textInputMessage = new JTextField();
        JButton createTopic = new JButton();
        JButton refreshTopicList = new JButton();

        createTopic.setText("Create new Topic");
        refreshTopicList.setText("Refresh topics list");

        textInputMessage.setEnabled(false);
        messageJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        topicJList.setModel(controller.topicsModel);
        topicJList.addListSelectionListener((e) -> {
            if (!e.getValueIsAdjusting()) {
                if (topicJList.isSelectionEmpty()) {
                    messageJList.setModel(new DefaultListModel<Message>());
                } else {
                    messageJList.setModel(topicJList.getSelectedValue().messagesModel);
                    if (!topicJList.getSelectedValue().subscribed) {
                        new SubPacket(topicJList.getSelectedValue().topicName).send(controller.os);
                        topicJList.getSelectedValue().subscribed = true;
                    }
                    textInputMessage.setEnabled(!(topicJList.isSelectionEmpty()));
                }
            }
        });

        textInputMessage.addActionListener((e) -> {
            String text = textInputMessage.getText();
            textInputMessage.setText("");
            new MsgPacket(topicJList.getSelectedValue().topicName, this.username, text).send(controller.os);
        });

        createTopic.addActionListener((e) -> {
            String topic = JOptionPane.showInputDialog(jFrame, "Insert topic name");
            new SubPacket(topic).send(controller.os);
            new TlrqPacket().send(controller.os);
        });

        refreshTopicList.addActionListener((e) -> {
            new TlrqPacket().send(controller.os);
        });

        jPanelTopics.add(createTopic, BorderLayout.PAGE_START);
        jPanelTopics.add(topicJList, BorderLayout.CENTER);
        jPanelTopics.add(refreshTopicList, BorderLayout.PAGE_END);

        jPanelChat.add(new JScrollPane(messageJList), BorderLayout.CENTER);
        jPanelChat.add(textInputMessage, BorderLayout.PAGE_END);

        jPanelContainer.add(jPanelTopics, BorderLayout.WEST);
        jPanelContainer.add(jPanelChat, BorderLayout.CENTER);

        jFrame.getContentPane().add(jPanelContainer);

        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(new Dimension(600, 400));
        jFrame.setVisible(true);
    }
}
