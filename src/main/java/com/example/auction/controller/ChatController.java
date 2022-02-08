package com.example.auction.controller;



import com.example.auction.Servise.WSService;
import com.example.auction.model.Message;
import com.example.auction.model.ResponseMessage;
import com.example.auction.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.util.HtmlUtils;

import java.security.Principal;
import java.text.ParseException;


@Controller
public class ChatController {

    @Autowired
    private WSService service;
    @Autowired
    private UserRepository userRepository;
    @Autowired



    @GetMapping("/chats")
    public String chat(Principal principal, Model model){
        var name =principal.getName();
        if (name.equals("Odmin")){
            model.addAttribute("recipient","Odmin2");
        }else
        {
            model.addAttribute("recipient","Odmin");
        }

        return "chat";
    }

    @MessageMapping("/private-message")
    @SendToUser("/topic/private-messages")
    public ResponseMessage getPrivateMessage(final Message message,
                                             final Principal principal,
                                             Model model) throws InterruptedException, ParseException {
        Thread.sleep(500);
        Message newMessage = new Message(message.getMessageContent(),principal.getName(),
                message.getRecipient(), message.getMessageSide());
        service.notifyUser(newMessage);
        return new ResponseMessage(HtmlUtils.htmlEscape(message.getMessageContent()),
                HtmlUtils.htmlEscape(message.getMessageSide()),
                HtmlUtils.htmlEscape(newMessage.getSender()),
                HtmlUtils.htmlEscape(newMessage.getStrTime())
        );
    }
}