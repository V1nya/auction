package com.example.auction.model;


import javax.persistence.*;
import java.util.Date;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String content;
    private Date time;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private User userGet;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private User userSet;

    public Message() {
    }

    public Message(String content){
        this.content=content;
    }
    public Message(String content, User userGet, User userSet) {
        this.content = content;
        this.time = new Date();
        this.userGet = userGet;
        this.userSet = userSet;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public User getUserGet() {
        return userGet;
    }

    public void setUserGet(User userGet) {
        this.userGet = userGet;
    }

    public User getUserSet() {
        return userSet;
    }

    public void setUserSet(User userSet) {
        this.userSet = userSet;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
