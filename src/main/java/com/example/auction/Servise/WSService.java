package com.example.auction.Servise;

import com.example.auction.model.Message;
import com.example.auction.model.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WSService {

    private final SimpMessagingTemplate messagingTemplate;
    private final NotificationService notificationService;

    @Autowired
    public WSService(SimpMessagingTemplate messagingTemplate, NotificationService notificationService) {
        this.messagingTemplate = messagingTemplate;
        this.notificationService = notificationService;
    }

    public void notifyFrontend(final String message) {
        ResponseMessage response = new ResponseMessage(message);
        notificationService.sendGlobalNotification();

        messagingTemplate.convertAndSend("/topic/messages", response);
    }

    public void notifyUser(Message message) {
        ResponseMessage response = new ResponseMessage(message.getMessageContent(), "left");

        notificationService.sendPrivateNotification("Odmin");
        messagingTemplate.convertAndSendToUser("Odmin", "/topic/private-messages", response);
    }
}
