package com.example.auction.controller;


import com.example.auction.model.Product;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Controller

@PreAuthorize("hasAuthority('Salesman')")
public class SalesmanController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

    private String path = getPath();


    @GetMapping("/addAuction")
    public String addAuction() {
        return "addAuction";
    }


    @PostMapping("/addAuction")
    public String addAuc(
            @RequestParam(value = "title") String title,
            @RequestParam(value = "full_text") String full_text,
            @RequestParam(value = "count") double count,
            @RequestParam(value = "timeStart") String timeStart,
            @RequestParam(value = "timeEnd") String timeEnd,
            @RequestParam(value = "fileImg") MultipartFile fileImg,
            Model model,
            Principal principal
    ) throws IOException, ParseException {

        var user = (User) ((Authentication) principal).getPrincipal();

        Product product = new Product(title,
                full_text,
                count,
                timeStart,
                timeEnd,
                "");
        if (fileImg != null && !fileImg.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(path);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + fileImg.getOriginalFilename();

            fileImg.transferTo(new File(path + resultFilename));

            product.setNameImg(resultFilename);
        }

        user.getProductList().add(product);
        productRepository.save(product);
        userRepository.save(user);


        model.addAttribute("products", user.getProductList());

        return "auctions";
    }

    @GetMapping("/auctions")
    public String auc(Model model,
                      Principal principal) {
        model.addAttribute("products", userRepository.findByName(((User) ((Authentication) principal).getPrincipal())
                .getName()).getProductList());

        return "auctions";
    }

    @GetMapping("/editAuction")
    public String detail(@RequestParam(value = "id") long id, Model model) {

        model.addAttribute("product", productRepository.findById(id).get());
        return "editAuction";
    }

    @PostMapping("/delAuction")
    public String del(@RequestParam(value = "id") long id,
                      Principal principal,
                      Model model) {
        var user = userRepository.findByName(((User) ((Authentication) principal).getPrincipal()).getName());

        for (int i = 0; i < user.getProductList().size(); i++
        ) {
            if (user.getProductList().get(i).getId() == id) {
                new File(path + user.getProductList().get(i).getNameImg()).delete();
                user.getProductList().remove(i);
                break;
            }
        }

        userRepository.saveAndFlush(user);
//        productRepository.flush();


        model.addAttribute("products", productRepository.findAll());
        return "auctions";
    }

    @PostMapping("/editAuction")
    public String edit(@RequestParam(value = "title") String title,
                       @RequestParam(value = "full_text") String full_text,
                       @RequestParam(value = "count") double count,
                       @RequestParam(value = "timeStart") String timeStart,
                       @RequestParam(value = "timeEnd") String timeEnd,
                       MultipartFile fileImg,
                       @RequestParam(value = "id") long id,
                       Principal principal,
                       Model model) throws IOException, ParseException {
        var user = userRepository.findByName(((User) ((Authentication) principal).getPrincipal()).getName());

        for (var product : user.getProductList()
        ) {
            if (product.getId() == id) {

                product.setName(title);

                product.setDescription(full_text);

                product.setPrice(count);

                product.setStartAuction(timeStart);
                product.setEndAuction(timeEnd);

                if (fileImg != null && !fileImg.getOriginalFilename().isEmpty()) {
                    File uploadDir = new File(path);
                    new File(path + product.getNameImg()).delete();
                    if (!uploadDir.exists()) {
                        uploadDir.mkdir();
                    }

                    String uuidFile = UUID.randomUUID().toString();
                    String resultFilename = uuidFile + "." + fileImg.getOriginalFilename();
                    fileImg.transferTo(new File(path + resultFilename));
                    product.setNameImg(resultFilename);
                }
                productRepository.save(product);
                break;
            }

        }
        userRepository.save(user);

        model.addAttribute("products", productRepository.findAll());
        return "auctions";
    }


    public String getPath() {
        String[] pat = getClass().getClassLoader().getResource(".").getPath().split("/");
        String path = "";
        for (var s : pat
        ) {
            if (s.equals("auction")) {
                path += "auction/src/main/resources/static/images/";
                break;
            } else {
                path += s + "/";
            }
        }
        return path.substring(1, path.length());

    }


}
