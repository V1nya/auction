package com.example.auction.Servise;

import com.example.auction.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WSService {

    private final SimpMessagingTemplate messagingTemplate;
    private final MessageService messageService;

    @Autowired
    public WSService(SimpMessagingTemplate messagingTemplate, MessageService messageService) {
        this.messagingTemplate = messagingTemplate;
        this.messageService = messageService;
    }

    public void notifyFrontend(final String message) {
        Message response = new Message(message);
        messageService.sendGlobalNotification();

        messagingTemplate.convertAndSend("/topic/messages", response);
    }

    public void notifyUser(final String id, final String message) {
        Message response = new Message(message);

        messageService.sendPrivateNotification(id);
        messagingTemplate.convertAndSendToUser(id, "/topic/private-messages", response);
    }
}
