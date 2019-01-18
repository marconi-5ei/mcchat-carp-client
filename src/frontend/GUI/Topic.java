package frontend.GUI;

import javax.swing.*;

public class Topic {
    public String topicName;
    public DefaultListModel<Message> messagesModel;
    public boolean subscribed;

    public Topic(String topicName) {
        this.topicName = topicName;
        this.messagesModel= new DefaultListModel<Message>();
        this.subscribed = false;
    }

    public void addMessages(Message... messages) {
        for (Message message : messages)
            messagesModel.addElement(message);
    }

    @Override
    public String toString() {
        return (this.subscribed) ? this.topicName + " (S)": this.topicName;
    }
}
