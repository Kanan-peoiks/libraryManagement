package com.example.librarymanagement.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationService {

    @Async
    public void sendBorrowNotification(String userEmail, Long orderId) {
        log.info("Asinxron bildiriş emalı başladı... [Thread: {}]", Thread.currentThread().getName());

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Bildiriş göndərilərkən xəta baş verdi", e);
        }

        log.info("Email uğurla göndərildi -> Email: {}, Order ID: {} [Thread: {}]",
                userEmail, orderId, Thread.currentThread().getName());
    }
}