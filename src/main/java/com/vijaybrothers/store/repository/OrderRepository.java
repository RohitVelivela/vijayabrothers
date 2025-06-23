package com.vijaybrothers.store.repository;

import com.vijaybrothers.store.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import com.vijaybrothers.store.model.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Long> {
    java.util.Optional<Order> findByOrderNumber(String orderNumber);

    Page<Order> findByOrderStatus(OrderStatus orderStatus, Pageable pageable);

    @Query("SELECT SUM(o.totalAmount) FROM Order o")
    java.math.BigDecimal sumTotalAmount();
    
    Page<Order> findByGuestId(Integer guestId, Pageable pageable);
}
