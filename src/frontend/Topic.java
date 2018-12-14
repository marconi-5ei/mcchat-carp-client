package frontend;

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
    public boolean equals(Object obj) {
        if (((Topic) obj).topicName == this.topicName)
            return true;
        return false;
    }

    @Override
    public String toString() {
        return topicName;
    }
}


