package com.vijaybrothers.store.service;

import com.vijaybrothers.store.dto.CartItemDto;
import com.vijaybrothers.store.dto.cart.CartItemRequest;
import com.vijaybrothers.store.dto.cart.CartView;
import com.vijaybrothers.store.model.Cart;
import com.vijaybrothers.store.model.CartItem;
import com.vijaybrothers.store.model.Product;
import com.vijaybrothers.store.repository.CartItemRepository;
import com.vijaybrothers.store.repository.CartRepository;
import com.vijaybrothers.store.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepo;
    private final CartItemRepository itemRepo;
    private final ProductRepository productRepo;

    /** Ensure a Cart exists for this ID (or create a new one). */
    @Transactional
    public Cart getOrCreateCart(String cartId) {
        return cartRepo.findById(cartId)
            .orElseGet(() -> cartRepo.save(
                Cart.builder()
                    .cartId(cartId)
                    .createdAt(Instant.now())
                    .build()));
    }

    /** Add a line to the cart, then return the updated view. */
    @Transactional
    public CartView addItem(String rawCartId, CartItemRequest req) {
        // 1) Create cart if needed
        String cartId = (rawCartId == null || rawCartId.isBlank())
            ? UUID.randomUUID().toString()
            : rawCartId;
        Cart cart = getOrCreateCart(cartId);

        // 2) Load product
        Product p = productRepo.findById(req.productId())
            .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        // 3) Save the new CartItem
        CartItem item = CartItem.builder()
            .cart(cart)
            .product(p)
            .quantity(req.quantity())
            .addedAt(Instant.now())
            .build();
        itemRepo.save(item);

        // 4) Build & return view
        return buildView(cart.getCartId());
    }

    /** Build a snapshot of the cart's current contents. */
    @Transactional(readOnly = true)
    public CartView buildView(String cartId) {
        List<CartItem> items = itemRepo.findByCart_CartId(cartId);
        BigDecimal sub = BigDecimal.ZERO;
        List<CartView.CartLine> lines = new ArrayList<>();

        for (CartItem ci : items) {
            BigDecimal lineTotal = ci.getProduct().getPrice()
                .multiply(BigDecimal.valueOf(ci.getQuantity()));
            sub = sub.add(lineTotal);
            lines.add(new CartView.CartLine(
                ci.getCartItemId(),
                ci.getProduct().getProductId(),
                ci.getProduct().getName(),
                ci.getProduct().getMainImageUrl(),
                ci.getProduct().getPrice(),
                ci.getQuantity(),
                lineTotal
            ));
        }
        return new CartView(cartId, lines, sub, sub);
    }

    /**
     * Update quantity of a cart item. If qty <= 0, removes the item.
     * Returns updated cart view.
     */
    @Transactional
    public CartView updateQty(Integer itemId, int qty) {
        CartItem item = itemRepo.findById(itemId)
            .orElseThrow(() -> new IllegalArgumentException("Cart item not found"));
        if (qty <= 0) {
            itemRepo.delete(item);
        } else {
            item.setQuantity(qty);
        }
        return buildView(item.getCart().getCartId());
    }

    /**
     * Update quantity of a cart item by productId. If qty <= 0, removes the item.
     * Returns updated cart view.
     */
    @Transactional
    public CartView updateByProductId(String cartId, Integer productId, Integer quantity) {
        CartItem item = itemRepo.findByCart_CartIdAndProduct_ProductId(cartId, productId)
            .orElseThrow(() -> new IllegalArgumentException("No item found for product in cart"));
        
        if (quantity == 0) {
            itemRepo.delete(item);
        } else {
            item.setQuantity(quantity);
            itemRepo.save(item);
        }
        return buildView(cartId);
    }

    /**
     * Delete a cart item by productId.
     * Returns updated cart view.
     */
    @Transactional
    public CartView deleteByProductId(String cartId, Integer productId) {
        CartItem item = itemRepo.findByCart_CartIdAndProduct_ProductId(cartId, productId)
            .orElseThrow(() -> new IllegalArgumentException("No cart item found for product"));
        itemRepo.delete(item);
        return buildView(cartId);
    }

    /**
     * Get cart item by productId.
     * Returns Optional<CartItemDto> or empty if not found.
     */
    @Transactional(readOnly = true)
    public Optional<CartItemDto> getItemByProductId(String cartId, Integer productId) {
        return itemRepo.findByCart_CartIdAndProduct_ProductId(cartId, productId)
            .map(CartItemDto::from);
    }

    /**
     * Get total count of items in cart.
     * Returns the sum of all quantities.
     */
    @Transactional(readOnly = true)
    public int getItemCount(String cartId) {
        return itemRepo.countByCart_CartId(cartId);
    }
}
