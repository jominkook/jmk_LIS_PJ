package com.yk.logistic.service.notification;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
	private final SimpMessagingTemplate messagingTemplate;
	 
	public void sendNotification(Long recipientId, String message) {
        String destination = "/topic/notifications/" + recipientId;
        messagingTemplate.convertAndSend(destination, message);
    }

}
