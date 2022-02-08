package com.example.auction.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {
    private String messageContent;
    private String sender;
    private String recipient;
    private String messageSide;
    private Date time;



    public Message(){}


    public Message(String messageContent, String sender, String recipient, String messageSide) {
        this.messageContent = messageContent;
        this.sender = sender;
        this.recipient = recipient;
        this.messageSide = messageSide;
        this.time = new Date();
    }


    public Date getTime() {
        return time;
    }
    public String getStrTime() throws ParseException {
        SimpleDateFormat formatDate = new SimpleDateFormat("HH:mm");
        return formatDate.format(time);
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getSender() {
        return sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessageSide() {
        return messageSide;
    }

    public void setMessageSide(String messageSide) {
        this.messageSide = messageSide;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }
}
