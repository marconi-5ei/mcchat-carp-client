package frontend;

import backend.Client;
import backend.packets.MsgPacket;
import backend.packets.TlPacket;

import javax.swing.*;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.LinkedList;

public class Controller {
    public DefaultListModel<Topic> topicsModel;

    public Controller() {
        this.topicsModel = new DefaultListModel<Topic>();
    }

    public void addTopics(TlPacket topics) {
        for (String topicName: topics.topics) {
            if (!topicsModel.contains(new Topic(topicName)))
                topicsModel.addElement(new Topic(topicName));
        }
    }

    public void addMessage(MsgPacket msg) {
        this.getTopic(msg.topic).addMessages(new Message(msg.message, msg.username));
    }

    private Topic getTopic(String topicName) throws InvalidParameterException {
        for (int i = 0; i < topicsModel.getSize(); ++i) {
            if (topicsModel.get(i).topicName == topicName)
                return topicsModel.get(i);
        }
        throw new InvalidParameterException();
    }
}
