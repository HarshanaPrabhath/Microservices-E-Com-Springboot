package com.ecom.microservices.services;


import com.ecom.microservices.dto.CartItemRequest;
import com.ecom.microservices.model.CartItem;
import com.ecom.microservices.model.Product;
import com.ecom.microservices.model.User;
import com.ecom.microservices.repositories.CartItemRepository;
import com.ecom.microservices.repositories.ProductRepository;
import com.ecom.microservices.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;


    public boolean addToCart(String userId, CartItemRequest request) {

        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
        if(userOpt.isEmpty()){
            return false;
        }

        Optional<Product> productOpt = productRepository.findById(request.getProductId());
        if(productOpt.isEmpty()){
            return false;
        }
        Product product = productOpt.get();
        if(product.getStockQuantity() < request.getQuantity()){
            return false;
        }

        User user = userOpt.get();

        CartItem existingCartItem = cartItemRepository.findByUserAndProduct(user,product);
        if(existingCartItem != null){
            existingCartItem.setQuantity(existingCartItem.getQuantity() + request.getQuantity());
            existingCartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(existingCartItem.getQuantity())));
            cartItemRepository.save(existingCartItem);
        }else{
            CartItem cartItem = new CartItem();
            cartItem.setUser(user);
            cartItem.setProduct(product);
            cartItem.setQuantity(request.getQuantity());
            cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
            cartItemRepository.save(cartItem);
        }

        return true;

    }

    public boolean deleteItemFromCart(String userId, Long productId) {
        try {
            Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
            if (userOpt.isEmpty()) {
                return false;
            }

            Optional<Product> productOpt = productRepository.findById(productId);
            if (productOpt.isEmpty()) {
                return false;
            }

            CartItem cartItem = cartItemRepository.deleteByUserAndProduct(userOpt.get(), productOpt.get());
            return cartItem != null;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public List<CartItem> getCart(String userId){
       return userRepository.findById(Long.valueOf(userId))
               .map(cartItemRepository::findByUser)
               .orElseGet(List::of);
    }

    public void clearCart(String userId){
        userRepository.findById(Long.valueOf(userId)).ifPresent(cartItemRepository::deleteByUser);
    }

}
