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
import java.util.Date;

public class Message {
    private String messageContent;
    private String sender;
    private String messageSide;

    public String getSender() {
        return sender;
    }

    public Message(){}

    public Message(String messageContent, String sender, String messageSide) {
        this.messageContent = messageContent;
        this.sender = sender;
        this.messageSide = messageSide;
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
