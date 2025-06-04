package com.example.ecommerce.service.impl;

import com.example.ecommerce.dto.ProductDto;
import com.example.ecommerce.dto.Response;
import com.example.ecommerce.entity.Category;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.exception.NotFoundException;
import com.example.ecommerce.mapper.EntityDtoMapper;
import com.example.ecommerce.repository.CategoryRepository;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    private EntityDtoMapper entityDtoMapper;

    @Override
    public Response createProduct(Long categoryId, String image, String name, String description, BigDecimal price) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new NotFoundException("Category not found"));
        Product product = new Product();
        product.setCategory(category);
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setImageUrl(image);
        productRepository.save(product);
        return Response.builder()
                .status(200)
                .message("Product create successfully")
                .build();
    }

    @Override
    public Response updateProduct(Long productId, Long categoryId, String image, String name, String description, BigDecimal price) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new NotFoundException("Product not found"));
        Category category = null;
        if (category != null) {
            category = categoryRepository.findById(categoryId).orElseThrow(() -> new NotFoundException("Category not found"));
        }
        if (category != null) product.setCategory(category);
        if (name != null) product.setName(name);
        if (description != null) product.setDescription(description);
        if (price != null) product.setPrice(price);
        if (image != null) product.setImageUrl(image);
        productRepository.save(product);
        return Response.builder()
                .status(200)
                .message("Product update successfully")
                .build();
    }

    @Override
    public Response deleteProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new NotFoundException("Product not found"));
        productRepository.delete(product);
        return Response.builder()
                .status(200)
                .message("Product delete successfully")
                .build();
    }

    @Override
    public Response getProductById(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new NotFoundException("Product not found"));
        ProductDto productDto = entityDtoMapper.mapProductToDtoBasic(product);
        return Response.builder()
                .status(200)
                .product(productDto)
                .build();
    }

    @Override
    public Response getAllProducts() {
        List<ProductDto> productList = productRepository.findAll().stream().map(entityDtoMapper::mapProductToDtoBasic).collect(Collectors.toList());
        return Response.builder()
                .status(200)
                .productList(productList)
                .build();
    }

    @Override
    public Response getProductByCategory(Long categoryId) {
        List<Product> products = productRepository.findByCategoryId(categoryId);
        if (products.isEmpty()) {
            throw new NotFoundException("No products found for this category");
        }
        List<ProductDto> productDtoList = products.stream().map(entityDtoMapper::mapProductToDtoBasic).collect(Collectors.toList());
        return Response.builder()
                .status(200)
                .productList(productDtoList)
                .build();
    }

    @Override
    public Response searchProduct(String searchValue) {
        List<Product> products = productRepository.findByNameContainingOrDescriptionContaining(searchValue, searchValue);
        if (products.isEmpty()){
            throw new NotFoundException("No product found");
        }
        List<ProductDto> productDtoList = products.stream().map(entityDtoMapper::mapProductToDtoBasic).collect(Collectors.toList());
        return Response.builder()
                .status(200)
                .productList(productDtoList)
                .build();
    }
}
