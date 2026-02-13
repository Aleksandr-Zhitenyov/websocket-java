package model;

public class SocketMessage {
    public String topic;
    public String type;
    public String subject;
    public SocketMessageData data;

    public SocketMessage() {
    }

    public String getTopic() {
        return topic;
    }

    public String getType() {
        return type;
    }

    public String getSubject() {
        return subject;
    }

    public SocketMessageData getData() {
        return data;
    }
}
