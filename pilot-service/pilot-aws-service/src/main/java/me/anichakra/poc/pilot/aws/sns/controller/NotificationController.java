package me.anichakra.poc.pilot.aws.sns.controller;

import me.anichakra.poc.pilot.aws.sns.domain.Notification;
import me.anichakra.poc.pilot.aws.sns.service.PublisherService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sns/notification")
public class NotificationController {

    @Autowired
    private PublisherService publisherService;

    @PostMapping("/publish")
    public boolean publishNotification(@RequestBody Notification notification) {

        try {
            publisherService.publish(notification.getSubject(), notification.getBody());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }


}










