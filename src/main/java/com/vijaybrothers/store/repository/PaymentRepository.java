package com.vijaybrothers.store.repository;

import com.vijaybrothers.store.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment,Integer> {
    Optional<Payment> findByOrderOrderId(Long orderId);
    Optional<Payment> findByTransactionId(String transactionId);
}
