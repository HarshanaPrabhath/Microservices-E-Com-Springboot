package com.ecom.microservices.controllers;


import com.ecom.microservices.dto.CartItemRequest;
import com.ecom.microservices.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        boolean deleted = cartService.deleteItemFromCart(userId,productId);

        return deleted ? ResponseEntity.noContent().build(): ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
