package com.example.auction.controller;


import com.example.auction.model.MyFiles;
import com.example.auction.model.Product;
import com.example.auction.model.User;
import com.example.auction.repo.ProductRepository;
import com.example.auction.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.security.Principal;
import java.time.LocalDate;
import java.util.Date;

@Controller

@PreAuthorize("hasAuthority('Salesman')")
public class SalesmanController {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;

    private String path=getPath();
    private static MyFiles files = new MyFiles();


    @GetMapping("/addAuction")
    public String addAuction(){return "addAuction";}

    @PostMapping("/addAuction")
    public String addAuc(
            @RequestParam(value = "title")  String title,
            @RequestParam(value = "full_text") String full_text,
            @RequestParam(value = "count") double count,
            @RequestParam(value = "timeStart") String timeStart,
            @RequestParam(value = "timeEnd") String timeEnd,
            @RequestParam("fileImg") MultipartFile fileImg,
            Model model,
            Principal principal
            ){

        var user=(User) ((Authentication) principal).getPrincipal();
        Product product = new Product(title,
                full_text,
                count,
                new Date(Integer.parseInt(timeStart.substring(0,4)),Integer.parseInt(timeStart.substring(5,7)),Integer.parseInt(timeStart.substring(8,10))),
                new Date(Integer.parseInt(timeEnd.substring(0,4)),Integer.parseInt(timeEnd.substring(5,7)),Integer.parseInt(timeEnd.substring(8,10))),
                user,
                "");



        if(fileImg.isEmpty())
        {
            throw  new RuntimeException("you will never see it( if you don't look at the console)) "
                    + " please provide a valid file)");
        }
        InputStream in = null;
        BufferedOutputStream out = null;
        try {
            in = new BufferedInputStream(fileImg.getInputStream());
            byte[] b = in.readAllBytes();
            String fullPath = decideFullPath(fileImg);
            product.setNameImg(fullPath.substring(fullPath.lastIndexOf('/')+1,fullPath.length()));
            out = new BufferedOutputStream(new FileOutputStream(fullPath));
            out.write(b);
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }



        int i = 0;
        user.getProductList().add(product);
        userRepository.save(user);




        return "";
    }

    private String getPath(){
        String[] pat = getClass().getClassLoader().getResource(".").getPath().split("/");
        String path="";
        for (var s :pat
             ) {
            if (s.equals("auction")){
                path+="auction/src/main/resources/static";
                break;
            }else {
                path+=s+"/";
            }
        }
        return path.substring(1,path.length());

    }

    private String decideFullPath(MultipartFile file) {
        String filename = file.getOriginalFilename();
//        +LocalDate.now().toString()
        int index = filename.indexOf('.');
        var newFileName =filename.substring(0,index)+LocalDate.now().toString()+filename.substring(index, filename.length());
        String extension = filename.substring(index+1, filename.length()).toUpperCase();

        if ("PNG".equals(extension) || "JPG".equals(extension) || "JPEG".equals(extension))
        {   files.setImage(newFileName);
            return path + "/" + "images" + "/" + newFileName;
        }

//        else if ("MP4".equals(extension) || "FLV".equals(extension))
//        {
//            files.setVideo(newFileName);
//            return path + "/" + "videos" +"/"+ newFileName;
//        }

        else
            throw new RuntimeException("extension not supported :"+extension);

    }

}
