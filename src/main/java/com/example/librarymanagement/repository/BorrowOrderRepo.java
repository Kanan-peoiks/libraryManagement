package com.example.librarymanagement.repository;

import com.example.librarymanagement.model.BorrowOrder;
import com.example.librarymanagement.model.OrderStatus;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface BorrowOrderRepo extends JpaRepository<BorrowOrder, Long> {

    // hem id, hem status ile birge
    List<BorrowOrder> findByUserIdAndStatus(Long userId, OrderStatus status);

//teyin olunaun tarixler araligi ucun
    @Query("SELECT o FROM BorrowOrder o WHERE o.orderDate BETWEEN :startDate AND :endDate AND o.status = :status")
    List<BorrowOrder> findOrdersInDateRange(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("status") OrderStatus status
    );

//id-e uygun olan user-in goturduklerini teyin etmek ucun
    @Query(value = """
            SELECT COUNT(bi.id) 
            FROM borrow_orders bo
            JOIN borrow_items bi ON bo.id = bi.borrow_order_id
            WHERE bo.user_id = :userId AND bo.status = 'ACTIVE'
            """, nativeQuery = true)
    Long countActiveBorrowedBooksByUserId(@Param("userId") Long userId);

    // checkpoint 5
    @EntityGraph(attributePaths = {"user", "items", "items.book"})
    List<BorrowOrder> findAll();
}