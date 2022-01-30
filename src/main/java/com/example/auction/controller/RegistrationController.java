package com.example.auction.controller;

import com.example.auction.model.ERole;
import com.example.auction.model.User;
import com.example.auction.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;

@Controller
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailSender mailSender;

    @GetMapping("/registration")
    public String reg() {

        return "registrationOne";
    }

    @PostMapping("/registration")
    public String regEmail(String email, Model model) {

        if (userRepository.findByEmail(email) != null) {
            model.addAttribute("message", "Пошта занята");
            return "registrationOne";

        } else {
            model.addAttribute("email", email);
            model.addAttribute("roles", ERole.values());
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo(email);
            msg.setSubject("Код для підтвердження");
            msg.setText("Ваш код:" + email.length() * 2094);

//            mailSender.send(msg);
            return "registrationTo";
        }

    }

    @PostMapping("/registrationTo")
    public String regUser(String username, String password, String code,
                          String email,
                          Model model,
                          @RequestParam(value = "idChecked", required = false) List<String> role) {

        if (code == null || Integer.parseInt(code) != email.length() * 2094) {
            model.addAttribute("message", "Код введено неправильно,відправлено повторно.");
            model.addAttribute("email", email);
            model.addAttribute("username", username);
            model.addAttribute("roles", ERole.values());
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo(email);
            msg.setSubject("Код для підтвердження");
            msg.setText("Ваш код:" + email.length() * 2094);
//            mailSender.send(msg);
            return "registrationTo";

        } else {
            User user = new User();
            user.setEmail(email);
            user.setName(username);
            if (ERole.Customer.toString().equals(role.get(0))){
                user.setRoles((Collections.singleton(ERole.Customer)));
            }
            else {
                user.setRoles((Collections.singleton(ERole.Salesman)));
            }
            user.setPassword(password);
            user.setCount(0);
            model.addAttribute("message", "Ви успішно зареєструвалися");
            return "login";
        }

    }

}



