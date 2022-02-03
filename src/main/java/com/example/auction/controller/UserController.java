package com.example.auction.controller;

import com.example.auction.model.User;
import com.example.auction.repo.ProductRepository;
import com.example.auction.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class UserController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/personalArea")
    public String personalArea(Model model, Principal principal){
        var user = userRepository.findByName(((User)((Authentication)principal).getPrincipal()).getName());
        model.addAttribute("buys",productRepository.findByUserBuy(user));
        model.addAttribute("leaders",productRepository.findByLeader(user));
        model.addAttribute("user",user);
        return "personalArea";
    }
}
