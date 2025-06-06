package com.example.ecommerce.mapper;

import com.example.ecommerce.dto.*;
import com.example.ecommerce.entity.*;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Component;

@Component
public class EntityDtoMapper {
    //user entity to user DTO
    public UserDto mapUserToDtoBasic(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRole().name());
        userDto.setName(user.getName());
        return userDto;
    }
    //Address to DTO basic
    public AddressDto mapAddressToDtoBasic(Address address) {
        AddressDto addressDto = new AddressDto();
        addressDto.setId(address.getId());
        addressDto.setStreet(address.getStreet());
        addressDto.setCity(address.getCity());
        addressDto.setState(address.getState());
        addressDto.setZipCode(address.getZipCode());
        return addressDto;
    }

    //Category to DTO basic
    public CategoryDto mapCategoryToDtoBasic(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        return categoryDto;
    }

    //OrderItem to DTO basic
    public OrderItemDto mapOrderItemToDtoBasic(OrderItem orderItem) {
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setId(orderItem.getId());
        orderItemDto.setQuantity(orderItem.getQuantity());
        orderItemDto.setPrice(orderItem.getPrice());
        orderItemDto.setStatus(orderItem.getStatus().name());
        orderItemDto.setCreatedAt(orderItemDto.getCreatedAt());
        return orderItemDto;
    }
    //Product to DTO basic
    public ProductDto mapProductToDtoBasic(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setCreatedAt(product.getCreatedAt());
        return productDto;
    }
    public UserDto mapUserToDtoPlusAddress(User user){
        UserDto userDto = mapUserToDtoBasic(user);
        if (user.getAddress() != null) {
            AddressDto addressDto = mapAddressToDtoBasic(user.getAddress());
            userDto.setAddress(addressDto);
        }
        return userDto;
    }

    //orderItem to DTO plus product
    public OrderItemDto mapOrderItemToDtoPlusProduct(OrderItem orderItem) {
        OrderItemDto orderItemDto = mapOrderItemToDtoBasic(orderItem);
        if (orderItem.getProduct() != null) {
            ProductDto productDto = mapProductToDtoBasic(orderItem.getProduct());
            orderItemDto.setProduct(productDto);
        }
        return orderItemDto;
    }

    //OrderItem to DTO plus user and product
    public OrderItemDto mapOrderItemToDtoPlusUserAndProduct(OrderItem orderItem) {
        OrderItemDto orderItemDto = mapOrderItemToDtoPlusProduct(orderItem);
        if (orderItem.getUser() != null) {
            UserDto userDto = mapUserToDtoBasic(orderItem.getUser());
            orderItemDto.setUser(userDto);
        }
        return orderItemDto;
    }

    //User to DTO with address and order items History
    public UserDto mapUserToDtoPlusAddressAndOrderItems(User user) {
        UserDto userDto = mapUserToDtoPlusAddress(user);
        if (user.getOrderItemList() != null && !user.getOrderItemList().isEmpty()) {
            for (OrderItem orderItem : user.getOrderItemList()) {
                OrderItemDto orderItemDto = mapOrderItemToDtoPlusUserAndProduct(orderItem);
                userDto.getOrderItemList().add(orderItemDto);
            }
        }
        return userDto;
    }
}
