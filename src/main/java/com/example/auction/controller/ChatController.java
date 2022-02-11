package com.example.auction.controller;



import com.example.auction.Servise.WSService;
import com.example.auction.model.Message;
import com.example.auction.model.ResponseMessage;
import com.example.auction.model.User;
import com.example.auction.repo.ChatRepository;
import com.example.auction.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.HtmlUtils;

import java.security.Principal;
import java.text.ParseException;
import java.util.stream.Collectors;


@Controller
public class ChatController {

    @Autowired
    private WSService service;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChatRepository chatRepository;



    @GetMapping("/chatRoom")
    public String chatRoom(Principal principal,Model model){

        var cht =userRepository.findByName(principal.getName()).getChats();
        for (var ch:cht
             ) {
            ch.setName(principal.getName());
        }
        model.addAttribute("chats", cht);
        return "chatRoom";
    }

    @GetMapping("/chat")
    public String chat(@RequestParam("id") Long id, Principal principal, Model model){

//        Only recipients

            model.addAttribute("recipient",
                    chatRepository.findById(id).get().getUsers().stream()
                            .filter(x->!(x.getName().equals(principal.getName())))
                            .collect(Collectors.toList()).get(0).getName());
            model.addAttribute("chat_id",id);
            var messages = chatRepository.findById(id).get().getSortMessage();
            for (var mes :messages){
                if (!mes.getSender().equals(principal.getName())){
                    mes.setMessageSide("left");
                }
            }
            model.addAttribute("mess",messages);

        return "chat";
    }

    @MessageMapping("/private-message")
    @SendToUser("/topic/private-messages")
    public ResponseMessage getPrivateMessage( final Message message,
                                             final Principal principal) throws InterruptedException, ParseException {
        Thread.sleep(500);
        Message newMessage = new Message(message.getMessageContent(),principal.getName(),
                message.getRecipient(), message.getMessageSide(), message.getChat_id());
        service.notifyUser(newMessage);
        var chat =  chatRepository.findById(Long.parseLong(message.getChat_id())).get();
        chat.addMessage(newMessage);
        chatRepository.save(chat);
        return new ResponseMessage(HtmlUtils.htmlEscape(message.getMessageContent()),
                HtmlUtils.htmlEscape(message.getMessageSide()),
                HtmlUtils.htmlEscape(newMessage.getSender()),
                HtmlUtils.htmlEscape(newMessage.getStrTime()),
                HtmlUtils.htmlEscape(newMessage.getChat_id())
        );
    }
}