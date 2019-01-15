package frontend.GUI;

import javax.swing.*;

public class Topic {
    public String topicName;
    public DefaultListModel<Message> messagesModel;

    public Topic(String topicName) {
        this.topicName = topicName;
        this.messagesModel= new DefaultListModel<Message>();
    }

    public void addMessages(Message... messages) {
        for (Message message : messages)
            messagesModel.addElement(message);
    }

    @Override
    public String toString() {
        return topicName;
    }
}
