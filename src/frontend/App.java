package frontend;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

public class App {
    public Controller controller;

    public App(Controller controller) {
        this.controller = controller;

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignore) {}

        JFrame jFrame = new JFrame("Carp Chat Client");

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());
        jPanel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        jPanel.setBackground(Color.WHITE);


        JList<Topic> topicJList = new JList<Topic>();
        JList<Message> messageJList = new JList<Message>();

        topicJList.setModel(controller.topicsModel);

        topicJList.addListSelectionListener((e) -> {
            if (!e.getValueIsAdjusting()) {
                messageJList.setModel(topicJList.getSelectedValue().messagesModel);
            }
        });

        jPanel.add(topicJList, BorderLayout.WEST);
        jPanel.add(messageJList, BorderLayout.CENTER);


        jFrame.getContentPane().add(jPanel);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setVisible(true);
    }
}
