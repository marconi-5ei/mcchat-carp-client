//TODO: Set username

package frontend.GUI;

import backend.packets.MsgPacket;
import backend.packets.SubPacket;
import backend.packets.TlrqPacket;
import frontend.Controller;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class App {
    public Controller controller;

    public App(Controller controller) throws IOException {
        this.controller = controller;

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
                if (topicJList.isSelectionEmpty())
                    messageJList.setModel(new DefaultListModel<Message>());
                else
                    messageJList.setModel(topicJList.getSelectedValue().messagesModel);
                textInputMessage.setEnabled(!(topicJList.isSelectionEmpty()));
            }
        });

        textInputMessage.addActionListener((e) -> {
            String text = textInputMessage.getText();
            textInputMessage.setText("");
            try {
                new MsgPacket(topicJList.getSelectedValue().topicName, "UserName", text).send(controller.os);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        createTopic.addActionListener((e) -> {
            String topic = JOptionPane.showInputDialog(jFrame, "Insert topic name");
            try {
                new SubPacket(topic).send(controller.os);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        refreshTopicList.addActionListener((e) -> {
            try {
                new TlrqPacket().send(controller.os);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        jPanelTopics.add(createTopic, BorderLayout.PAGE_START);
        jPanelTopics.add(topicJList, BorderLayout.CENTER);
        jPanelTopics.add(refreshTopicList, BorderLayout.PAGE_END);

        jPanelChat.add(messageJList, BorderLayout.PAGE_START);
        jPanelChat.add(textInputMessage, BorderLayout.PAGE_END);

        jPanelContainer.add(jPanelTopics, BorderLayout.WEST);
        jPanelContainer.add(jPanelChat, BorderLayout.CENTER);

        jFrame.getContentPane().add(jPanelContainer);

        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(new Dimension(300, 400));
        jFrame.setVisible(true);
    }
}
