package com.vijaybrothers.store.repository.payments;
import com.vijaybrothers.store.repository.payments.PaymentRepository;

import com.vijaybrothers.store.model.payments.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByOrderId(String orderId);
}
