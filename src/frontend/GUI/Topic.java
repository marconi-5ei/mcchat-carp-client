package frontend.GUI;

import javax.swing.*;
import java.util.Arrays;

public class Topic {
    public String topicName;
    public DefaultListModel<Message> messagesModel;

    public Topic(String topicName) {
        this.topicName = topicName;
        this.messagesModel= new DefaultListModel<Message>();
    }

    public void addMessages(Message... messages) {
        messagesModel.addAll(Arrays.asList(messages));
    }

    @Override
    public String toString() {
        return topicName;
    }
}
