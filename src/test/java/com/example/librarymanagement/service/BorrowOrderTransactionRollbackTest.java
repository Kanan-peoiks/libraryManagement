package com.example.librarymanagement.service;

import com.example.librarymanagement.dto.BorrowItemRequestDTO;
import com.example.librarymanagement.dto.CreateBorrowOrderRequestDTO;
import com.example.librarymanagement.model.Role;
import com.example.librarymanagement.model.User;
import com.example.librarymanagement.repository.BorrowOrderRepo;
import com.example.librarymanagement.repository.UserRepo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class BorrowOrderTransactionRollbackTest {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BorrowOrderRepo borrowOrderRepo;

    @Autowired
    private BorrowOrderService borrowOrderService;

    @Test
    @DisplayName("Kitab tapılmadıqda tranzaksiyanın rollback olunması testi")
    void testTransactionRollback_WhenBookNotFound() {
        // 1. İlkin vəziyyətdə bazadakı sifariş sayını götürürük
        long initialCount = borrowOrderRepo.count();

        User user = new User();
        user.setEmail("rollback_test@example.com");
        user.setFullName("Test User");
        user.setPassword("password123");
        user.setRole(Role.USER);
        user = userRepo.save(user);

        BorrowItemRequestDTO item = new BorrowItemRequestDTO();
        item.setBookId(9999L);

        CreateBorrowOrderRequestDTO request = new CreateBorrowOrderRequestDTO();
        request.setUserId(user.getId());
        request.setItems(List.of(item));

        assertThrows(Exception.class, () -> {
            borrowOrderService.createBorrowOrder(request);
        });

        long finalCount = borrowOrderRepo.count();
        assertEquals(initialCount, finalCount, "Xəta zamanı tranzaksiya rollback olunmalı və bazaya yeni record düşməməlidir.");
    }
}