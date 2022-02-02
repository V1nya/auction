package com.example.auction.model;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
            orphanRemoval = true, fetch = FetchType.EAGER)
    private User userBuy;

    public Product() {
    }

    public Product(String name,
                   String description,
                   double price,
                   Date startAuction,
                   Date endAuction,
                   String nameImg) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.startAuction = startAuction;
        this.endAuction = endAuction;
        this.nameImg = nameImg;

    }
    public Product(String name,
                   String description,
                   double price,
                   String startAuction,
                   String  endAuction,
                   String nameImg) throws ParseException {
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        this.name = name;
        this.description = description;
        this.price = price;
        this.startAuction = formatDate.parse(startAuction);
        this.endAuction = formatDate.parse(endAuction);
        this.nameImg = nameImg;


    }
        public String getNameImg() {
        return nameImg;
    }

    public void setNameImg(String nameImg) {
        this.nameImg = nameImg;
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

    public String getStartAuctionStr() throws ParseException {

        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");


        return formatDate.format(startAuction);
    }

    public String getEndAuctionStr() {
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        return formatDate.format(endAuction);

    }

    public Date getStartAuction() {
        return startAuction;
    }

    public void setStartAuction(Date startAuction) {
        this.startAuction = startAuction;
    }
    public void setStartAuction(String startAuction) throws ParseException {
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");

        this.startAuction = formatDate.parse(startAuction);
    }

    public Date getEndAuction() {
        return endAuction;
    }

    public void setEndAuction(Date endAuction) {
        this.endAuction = endAuction;
    }
    public void setEndAuction(String endAuction) throws ParseException {
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        this.endAuction = formatDate.parse(endAuction);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
