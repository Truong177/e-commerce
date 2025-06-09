package com.example.ecommerce.dto;

import com.example.ecommerce.entity.Address;
import com.example.ecommerce.entity.OrderItem;
import com.example.ecommerce.entity.emum.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String role;
    private List<OrderItemDto> orderItemList = new ArrayList<>();
    private AddressDto address;
    private LocalDateTime createdAt;
}
