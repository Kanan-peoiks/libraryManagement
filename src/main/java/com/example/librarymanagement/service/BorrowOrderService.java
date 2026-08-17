package com.example.librarymanagement.service;

import com.example.librarymanagement.dto.BorrowItemRequestDTO;
import com.example.librarymanagement.dto.BorrowItemResponseDTO;
import com.example.librarymanagement.dto.BorrowOrderResponseDTO;
import com.example.librarymanagement.dto.CreateBorrowOrderRequestDTO;
import com.example.librarymanagement.exception.NotFoundException;
import com.example.librarymanagement.model.Book;
import com.example.librarymanagement.model.BorrowItem;
import com.example.librarymanagement.model.BorrowOrder;
import com.example.librarymanagement.model.OrderStatus;
import com.example.librarymanagement.model.User;
import com.example.librarymanagement.repository.BookRepe;
import com.example.librarymanagement.repository.BorrowOrderRepo;
import com.example.librarymanagement.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BorrowOrderService {

    private final BorrowOrderRepo borrowOrderRepo;
    private final UserRepo userRepo;
    private final BookRepe bookRepo;
    private final NotificationService notificationService; // Task 4: Asinxron bildiriş servisi

    @Transactional
    public BorrowOrderResponseDTO createBorrowOrder(CreateBorrowOrderRequestDTO request) {
        // 1. İstifadəçini tapmaq
        User user = userRepo.findById(request.getUserId())
                .orElseThrow(() -> new NotFoundException("İstifadəçi tapılmadı. ID: " + request.getUserId()));

        BorrowOrder order = BorrowOrder.builder()
                .user(user)
                .orderDate(LocalDateTime.now())
                .status(OrderStatus.ACTIVE)
                .items(new ArrayList<>())
                .build();

        // Kitablar üçün
        for (BorrowItemRequestDTO itemDTO : request.getItems()) {
            Book book = bookRepo.findById(itemDTO.getBookId())
                    .orElseThrow(() -> new NotFoundException("Kitab tapılmadı. ID: " + itemDTO.getBookId()));

            BorrowItem item = BorrowItem.builder()
                    .book(book)
                    .daysRequested(itemDTO.getDaysRequested())
                    .build();

            order.addBorrowItem(item);
        }

        BorrowOrder savedOrder = borrowOrderRepo.save(order);

        notificationService.sendBorrowNotification(user.getEmail(), savedOrder.getId());

        return mapToResponseDTO(savedOrder);
    }

    private BorrowOrderResponseDTO mapToResponseDTO(BorrowOrder order) {
        List<BorrowItemResponseDTO> itemDTOs = order.getItems().stream()
                .map(item -> BorrowItemResponseDTO.builder()
                        .itemId(item.getId())
                        .bookId(item.getBook().getId())
                        .bookTitle(item.getBook().getTitle())
                        .daysRequested(item.getDaysRequested())
                        .build())
                .toList();

        return BorrowOrderResponseDTO.builder()
                .orderId(order.getId())
                .userId(order.getUser().getId())
                .userEmail(order.getUser().getEmail())
                .orderDate(order.getOrderDate())
                .status(order.getStatus())
                .items(itemDTOs)
                .build();
    }
}