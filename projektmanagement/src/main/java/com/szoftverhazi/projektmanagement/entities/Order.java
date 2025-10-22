package com.szoftverhazi.projektmanagement.entities;

import com.szoftverhazi.projektmanagement.dto.OrderDto;
import com.szoftverhazi.projektmanagement.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Data
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Project project;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;

    private String details;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public OrderDto toOrderDto() {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(this.id);
        orderDto.setCustomerId(this.customer.getId());
        orderDto.setDetails(this.details);
        orderDto.setStatus(this.status);
        return orderDto;
    }
}
