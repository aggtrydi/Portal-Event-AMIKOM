package com.example.mobile.portaleventamikom.Model;

public class ModelChat {

     String receiver,sender,message,timestamp;
    boolean isSend;

    public ModelChat() {
    }


    public ModelChat(String reciver, String sender, String message, String timestamp, boolean isSend) {
        this.receiver = reciver;
        this.sender = sender;
        this.message = message;
        this.timestamp = timestamp;
        this.isSend = isSend;
    }

    public String getReciver() {
        return receiver;
    }

    public void setReciver(String reciver) {
        this.receiver = reciver;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isSend() {
        return isSend;
    }

    public void setSend(boolean send) {
        isSend = send;
    }
}
