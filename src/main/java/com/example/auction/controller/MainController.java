package com.example.auction.controller;

import com.example.auction.model.ERole;
import com.example.auction.model.User;
import com.example.auction.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class MainController {

    @Autowired
    private MailSender mailSender;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String getMail(Principal principal) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("kyrylopysanka@gmail.com");
        msg.setSubject("Тest");
        msg.setText("Test");
//        mailSender.send(msg);
//        var user = (User) ((Authentication) principal).getPrincipal();
//        user.setBalance(1000);
//        userRepository.save(user);
//         userRepository.delete( userRepository.findByEmail("krotgametv2@gmail.com"));
        return "main";
    }
    @GetMapping("/get")
    public String get (){
        return "get";
    }

}
