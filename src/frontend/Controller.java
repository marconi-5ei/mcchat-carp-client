package frontend;

import backend.packets.MsgPacket;
import backend.packets.TlPacket;
import frontend.GUI.Message;
import frontend.GUI.Topic;

import javax.swing.*;
import java.io.DataOutputStream;
import java.security.InvalidParameterException;

public class Controller {
    public DefaultListModel<Topic> topicsModel;
    public DataOutputStream os;

    public Controller(DataOutputStream os) {
        this.topicsModel = new DefaultListModel<Topic>();
        this.os = os;
    }

    public void addTopics(TlPacket topics) {
        for (String topicName: topics.topics) {
            if (!modelContainsTopic(topicName))
                topicsModel.addElement(new Topic(topicName));
        }
    }

    public void addMessage(MsgPacket msg) throws InvalidParameterException {
        this.getTopic(msg.topic).addMessages(new Message(msg.message, msg.username));
    }

    private Topic getTopic(String topicName) throws InvalidParameterException {
        for (int i = 0; i < topicsModel.getSize(); ++i) {
            if (topicsModel.get(i).topicName.equals(topicName))
                return topicsModel.get(i);
        }
        throw new InvalidParameterException();
    }

    private boolean modelContainsTopic(String topicName) {
        for (int i = 0; i < topicsModel.getSize(); ++i) {
            if (topicsModel.get(i).topicName.equals(topicName))
                return true;
        }
        return false;
    }
}
