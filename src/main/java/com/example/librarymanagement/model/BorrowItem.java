package com.example.librarymanagement.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "borrow_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BorrowItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "borrow_order_id", nullable = false)
    private BorrowOrder borrowOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(nullable = false)
    private Integer daysRequested;
}