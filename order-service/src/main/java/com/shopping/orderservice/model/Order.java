package com.shopping.orderservice.model;

import jakarta.persistence.*;
import jakarta.persistence.Id;
import lombok.*;

import java.util.List;


@Entity(name="t_orders")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Order {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;
    private String orderNumber;
    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderLineItems> orderLineItemsList;
}
