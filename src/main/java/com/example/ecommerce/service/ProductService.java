package com.example.ecommerce.service;

import com.example.ecommerce.dto.Response;
import com.example.ecommerce.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

public interface ProductService {
    Response createProduct(Long categoryId, String image, String name, String description, BigDecimal price);

    Response updateProduct(Long productId, Long categoryId, String image, String name, String description, BigDecimal price);
    Response deleteProduct(Long productId);
    Response getProductById(Long productId);
    Response getAllProducts();
    Response getProductByCategory(Long categoryId);
    Response searchProduct(String searchValue);


}
