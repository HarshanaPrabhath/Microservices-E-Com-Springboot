package com.ecom.microservices.repositories;

import com.ecom.microservices.model.CartItem;
import com.ecom.microservices.model.Product;
import com.ecom.microservices.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Integer> {

    CartItem findByUserAndProduct(User user, Product product);

    CartItem deleteByUserAndProduct(User user, Product product);

   List<CartItem> findByUser(User user);

    void deleteByUser(User user);
}
