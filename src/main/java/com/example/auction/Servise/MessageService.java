package com.example.auction.Servise;

import com.example.auction.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public MessageService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendGlobalNotification() {
        Message message = new Message("Global Notification");

        messagingTemplate.convertAndSend("/topic/global-notifications", message);
    }

    public void sendPrivateNotification(final String userId) {
        Message message = new Message("Private Notification");

        messagingTemplate.convertAndSendToUser(userId,"/topic/private-notifications", message);
    }
}
