package com.example.ecommerce.controller;

import com.example.ecommerce.dto.Response;
import com.example.ecommerce.exception.InvalidCredentialsException;
import com.example.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> createProduct(@RequestParam Long categoryId,
                                                  @RequestParam String image,
                                                  @RequestParam String name,
                                                  @RequestParam String description,
                                                  @RequestParam BigDecimal price) {
        if (categoryId != null || image.isEmpty() || name.isEmpty() || description.isEmpty() || price == null) {
            throw new InvalidCredentialsException("All file are required");
        }
        return ResponseEntity.ok(productService.createProduct(categoryId, image, name, description, price));
    }

    @PostMapping("/update/{productId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> updateProduct(
            @PathVariable Long productId,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String image,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) BigDecimal price){
        return ResponseEntity.ok(productService.updateProduct(productId,categoryId,image,name,description,price));
    }
    @DeleteMapping("/delete/{productId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> deleteProduct(@PathVariable Long productId){
        return ResponseEntity.ok(productService.deleteProduct(productId));
    }

    @GetMapping("get-all-productId/{productId}")
    public ResponseEntity<Response> getProductById(@PathVariable Long productId){
        return ResponseEntity.ok(productService.getProductById(productId));
    }
    @GetMapping("get-all")
    public ResponseEntity<Response> getAllProduct(){
        return ResponseEntity.ok(productService.getAllProducts());
    }
    @GetMapping("get-by-category-id/{categoryId}")
    public ResponseEntity<Response> getProductByCategory(@PathVariable Long categoryId){
        return ResponseEntity.ok(productService.getProductByCategory(categoryId));
    }
    @GetMapping("/search")
    public ResponseEntity<Response> searchProduct(@RequestParam String searchValue){
        return ResponseEntity.ok(productService.searchProduct(searchValue));
    }
}
