package com.ecom.microservices.services;

import com.ecom.microservices.dto.ProductRequest;
import com.ecom.microservices.dto.ProductResponse;
import com.ecom.microservices.model.Product;
import com.ecom.microservices.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = modelMapper.map(productRequest, Product.class);
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductResponse.class);
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(product -> modelMapper.map(product, ProductResponse.class))
                .collect(Collectors.toList());
    }

    public ProductResponse getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.map(value -> modelMapper.map(value, ProductResponse.class)).orElse(null);
    }

    public ProductResponse updateProduct(Long id, ProductRequest productRequest) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty()) {
            return null;
        }

        Product existingProduct = productOptional.get();
        // update fields from request
        modelMapper.map(productRequest, existingProduct);

        Product updatedProduct = productRepository.save(existingProduct);
        return modelMapper.map(updatedProduct, ProductResponse.class);
    }

    public boolean deleteProduct(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty()) {
            return false;
        }
        productRepository.delete(productOptional.get());
        return true;
    }

    public List<ProductResponse> getByKeyword(String name) {

        List<Product> products  = productRepository.searchProductByName(name);


        return products.stream()
                .map(product -> modelMapper.map(product,ProductResponse.class))
                .collect(Collectors.toList());
    }
}
