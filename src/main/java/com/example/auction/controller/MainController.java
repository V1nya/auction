package com.example.auction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    private MailSender mailSender;

    @GetMapping("/")
    public String getMail() {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("kyrylopysanka@gmail.com");
        msg.setSubject("Тest");
        msg.setText("Test");
        mailSender.send(msg);
        return "main";
    }
}
