package com.example.ecommerce.specification;

import com.example.ecommerce.entity.OrderItem;
import com.example.ecommerce.entity.emum.OrderStatus;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class OrderItemSpecification {
    public static Specification<OrderItem> hasStatus(OrderStatus status){
        return ((root, query, criteriaBuilder) -> status != null ?
                criteriaBuilder.equal(root.get("status"), status) : criteriaBuilder.conjunction());
    }
    public static Specification<OrderItem> createdBetween(LocalDateTime startDate,LocalDateTime endDate){
        return ((root, query, criteriaBuilder) ->{
            if (startDate != null && endDate != null){
                return criteriaBuilder.between(root.get("createdAt"), startDate, endDate);
            } else if (startDate != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), startDate);
            } else if (endDate != null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), endDate);
            } else {
                return criteriaBuilder.conjunction();
            }
        } );
    }
    public static Specification<OrderItem> hasItemId(Long itemId){
        return ((root, query, criteriaBuilder) -> itemId != null ?
                criteriaBuilder.equal(root.get("item").get("id"), itemId) : criteriaBuilder.conjunction());
    }
}
