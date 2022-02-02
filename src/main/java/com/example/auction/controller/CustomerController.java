package com.example.auction.controller;

import com.example.auction.model.User;
import com.example.auction.repo.ProductRepository;
import com.example.auction.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@PreAuthorize("hasAuthority('Customer')")
public class CustomerController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    @GetMapping("/allAuctions")
    public String allAuctions(Model model) {
        model.addAttribute("products", productRepository.findAll());
        return "allAuctions";
    }

    @GetMapping("/detailAuctions")
    public String detail(@RequestParam(value = "id") long id,
                         Model model) {
        model.addAttribute("product", productRepository.findById(id).get());
        model.addAttribute("leader", productRepository.findById(id).get().getLeader());
        model.addAttribute("prices", new int[]{25, 50, 100});
        return "detailAuctions";
    }

    @PostMapping("/addPriceAuction")
    public String addPrice(@RequestParam(value = "id") long id,
                           @RequestParam(value = "price") double price,
                           Principal principal,
                           Model model) {
        var user = userRepository.findByName(((User) ((Authentication) principal).getPrincipal())
                .getName());
        var product = productRepository.findById(id).get();
        if (user.getBalance()<product.getPrice()+price){
            model.addAttribute("money",product.getPrice()+price-user.getBalance());
            return "noMoney";
        }
        if (product.getLeader() != null && user.getId() == product.getLeader().getId()) {
            user.setBalance(user.getBalance() - price);
            product.setPrice(product.getPrice() + price);
        } else {
            if (product.getLeader() == null) {
                product.setLeader(user);
            } else {
                var us = userRepository.findByName(product.getLeader().getName());
                us.setBalance(us.getBalance() + product.getPrice());
                userRepository.save(us);
                product.setLeader(user);
            }
            user.setBalance(user.getBalance() - (price + product.getPrice()));
            product.setPrice(product.getPrice() + price);

        }
        userRepository.save(user);
        productRepository.save(product);
        model.addAttribute("product", productRepository.findById(id).get());
        model.addAttribute("prices", new int[]{25, 50, 100});
        model.addAttribute("leader", product.getLeader());
        return "detailAuctions";
    }
}
