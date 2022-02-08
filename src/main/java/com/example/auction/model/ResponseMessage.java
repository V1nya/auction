package com.example.auction.model;


import java.util.Date;

public class ResponseMessage {
    private String content;
    private String messageSide;
    private String sender;
    private String recipient;
    private String time;


    public ResponseMessage() {
    }

    public ResponseMessage(String content) {
        this.content = content;
    }

    public ResponseMessage(String content, String messageSide) {
        this.content = content;
        this.messageSide = messageSide;
    }

    public ResponseMessage(String content, String messageSide, String sender, String recipient, String time) {
        this.content = content;
        this.messageSide = messageSide;
        this.sender = sender;
        this.recipient = recipient;
        this.time = time;
    }
    public ResponseMessage(String content, String messageSide, String sender, String time) {
        this.content = content;
        this.messageSide = messageSide;
        this.sender = sender;
        this.time = time;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessageSide() {
        return messageSide;
    }

    public void setMessageSide(String messageSide) {
        this.messageSide = messageSide;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
