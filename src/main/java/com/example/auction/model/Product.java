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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
