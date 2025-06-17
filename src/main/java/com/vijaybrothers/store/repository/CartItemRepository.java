package com.vijaybrothers.store.repository;

import com.vijaybrothers.store.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    List<CartItem> findByCart_CartId(String cartId);
}
