package com.ecom.microservices.repositories;

import com.ecom.microservices.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    @Query("SELECT p FROM products p WHERE p.stockQuantity > 0 AND LOWER(p.name) LIKE LOWER(CONCAT('%', :name , '%') ) ")
    List<Product> searchProductByName(@Param("name")String name);
}
