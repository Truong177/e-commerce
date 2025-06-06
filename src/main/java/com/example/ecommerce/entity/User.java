package com.example.ecommerce.entity;

import com.example.ecommerce.entity.emum.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "users")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Name is required")
    private String name;
    @Column(unique = true)// Cột này phải có giá trị duy nhất, không được trùng lặp
    @NotBlank(message = "Email is required")
    private String email;
    @NotBlank(message = "Password is required")
    private String password;
    @Column(name = "phone_number")
    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    private UserRole role;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderItem> orderItemList;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)// Quan hệ 1-1, tải lười, tự động cascade tất cả thao tác
    private Address address;

    @Column(name = "created_at", updatable = false)// Cột này không được cập nhật sau khi tạo
    private final LocalDateTime createdAt = LocalDateTime.now();// Thời điểm tạo đối tượng, không thay đổi sau khi gán








}
