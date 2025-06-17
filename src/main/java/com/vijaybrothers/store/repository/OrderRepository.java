package com.vijaybrothers.store.repository;

import com.vijaybrothers.store.model.Order;
import com.vijaybrothers.store.model.OrderStatus;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import java.util.Optional;
import java.math.BigDecimal;

public interface OrderRepository extends JpaRepository<Order,Integer> {    
    /**
     * Find orders by status with pagination
     */
    Page<Order> findByOrderStatus(OrderStatus status, Pageable pageable);
    Optional<Order> findByOrderNumber(String orderNumber);
    
    /**
     * Find orders by guest ID sorted by creation date descending
     */
    Page<Order> findByGuestIdOrderByCreatedAtDesc(String guestId, Pageable pageable);

    /** Sum of all orders' totalAmount */
    @Query("SELECT COALESCE(SUM(o.totalAmount),0) FROM Order o")
    BigDecimal sumTotalAmount();
}
