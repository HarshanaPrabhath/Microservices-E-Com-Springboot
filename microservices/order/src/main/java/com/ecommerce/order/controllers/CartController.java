package com.ecommerce.order.controllers;



import com.ecommerce.order.dto.CartItemRequest;
import com.ecommerce.order.model.CartItem;
import com.ecommerce.order.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<String> addToCart(@RequestHeader("X-User-ID") String userId,
                                          @RequestBody CartItemRequest request){
        if(!cartService.addToCart(userId,request)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product Out of stock or User not found");
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<Void> removeFromCart(
            @RequestHeader("X-User-ID") String userId,
            @PathVariable Long productId
    ){
        boolean deleted = cartService.deleteItemFromCart(userId, String.valueOf(productId));

        return deleted ? ResponseEntity.noContent().build(): ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping
    public ResponseEntity<List<CartItem>> getCart  (
            @RequestHeader("X-User-ID") String userId){

      return ResponseEntity.ok(cartService.getCart(userId));
    }
}
