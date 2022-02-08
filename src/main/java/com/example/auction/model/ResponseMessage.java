package com.example.auction.model;


public class ResponseMessage {
    private String content;
    private String messageSide;


    public ResponseMessage() {
    }

    public ResponseMessage(String content) {
        this.content = content;
    }

    public ResponseMessage(String content, String messageSide) {
        this.content = content;
        this.messageSide = messageSide;
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
