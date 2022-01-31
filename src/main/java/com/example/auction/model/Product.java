package com.example.auction.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "prod")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String description;
    private double price;
    private Date startAuction;
    private Date endAuction;
    private String nameImg;

    @OneToOne(cascade = CascadeType.ALL,
            orphanRemoval = true,fetch = FetchType.EAGER)
    private User userSalesman;

    @OneToOne(cascade = CascadeType.ALL,
            orphanRemoval = true,fetch = FetchType.EAGER)
    private User userBuy;

    public  Product(){}

    public Product(String name,
                   String description,
                   double price,
                   Date startAuction,
                   Date endAuction,
                   User userSalesman,
                   String nameImg) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.startAuction = startAuction;
        this.endAuction = endAuction;
        this.userSalesman = userSalesman;
        this.nameImg=nameImg;
    }

    public String getNameImg() {
        return nameImg;
    }

    public void setNameImg(String nameImg) {
        this.nameImg = nameImg;
    }

    public User getUserSalesman() {
        return userSalesman;
    }

    public void setUserSalesman(User userSalesman) {
        this.userSalesman = userSalesman;
    }

    public User getUserBuy() {
        return userBuy;
    }

    public void setUserBuy(User userBuy) {
        this.userBuy = userBuy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getStartAuction() {
        return startAuction;
    }

    public void setStartAuction(Date startAuction) {
        this.startAuction = startAuction;
    }

    public Date getEndAuction() {
        return endAuction;
    }

    public void setEndAuction(Date endAuction) {
        this.endAuction = endAuction;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
