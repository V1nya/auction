package com.example.auction.controller;


import com.example.auction.Servise.WSService;
import com.example.auction.model.Message;
import com.example.auction.model.ResponseMessage;
import com.example.auction.model.User;
import com.example.auction.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.util.HtmlUtils;

import java.security.Principal;

@Controller
public class ChatController {

    @Autowired
    private WSService service;
    @Autowired
    private UserRepository userRepository;
    @Autowired

    @GetMapping("/chat")
    public String getChat(){

        return "chat";
    }

    @MessageMapping("/private-message")
    @SendToUser("/topic/private-messages")
    public ResponseMessage getPrivateMessage(final Message message,
                                             final Principal principal) throws InterruptedException {
        Thread.sleep(500);
        var s = "Odmin";
        service.notifyUser(message);
        return new ResponseMessage(HtmlUtils.htmlEscape(message.getMessageContent()),
                HtmlUtils.htmlEscape(message.getMessageSide())
        );
    }
}