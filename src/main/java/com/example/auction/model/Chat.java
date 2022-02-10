package com.example.auction.model;

import javax.persistence.*;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String userName;


    @OneToMany(cascade = CascadeType.ALL,
            orphanRemoval = true,fetch = FetchType.EAGER)
    @JoinTable(
            name = "chat_user",
            joinColumns = {@JoinColumn(name = "cht_id")},
            inverseJoinColumns = {@JoinColumn(name = "usr_id")}
    )
    private List<User> usersList;

    @OneToMany(cascade = CascadeType.ALL,
            orphanRemoval = true,fetch = FetchType.LAZY)
    private List<Message> messages;



    public Chat(){}



    public void setName(String userName) {
        this.userName = userName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {

        String name = " ";
        for (var user:usersList
             ) {
            if (!userName.equals(user.getName()) && name.equals(" ")){
                name+=user.getName()+"";
            }
            else if (!userName.equals(user.getName())){
                name+=user.getName()+", ";
            }
        }
        return name;
    }



    public List<User> getUsers() {
        return usersList;
    }

    public void setUsers(List<User> users) {
        this.usersList = users;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public List<Message> getSortMessage(){

       return messages.stream().sorted(Comparator.comparing(Message::getTime)).collect(Collectors.toList());
    }
}
