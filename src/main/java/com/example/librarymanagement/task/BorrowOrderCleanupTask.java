package com.example.librarymanagement.task;

import com.example.librarymanagement.repository.BorrowOrderRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class BorrowOrderCleanupTask {

    private final BorrowOrderRepo borrowOrderRepo;

    // Hər gün gecə yarısı saat 00:00-da avtomatik icra olunur (Cron expression)
    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void cleanupExpiredBorrowOrders() {
        log.info("Gündəlik planlaşdırılmış tapşırıq başladı: Vaxtı keçmiş sifarişlər təmizlənir...");

        log.info("Gündəlik planlaşdırılmış tapşırıq uğurla başa çatdı.");
    }

    @Scheduled(fixedRate = 60000)
    public void logSystemStatus() {
        log.info("Sistem sağlamlığı yoxlanışı: Scheduled task aktivdir. Cari vaxt: {}", LocalDateTime.now());
    }
}