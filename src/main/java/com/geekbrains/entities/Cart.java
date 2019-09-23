package com.geekbrains.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Table(name = "cart")
@Data
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_seq")
    @SequenceGenerator(name = "cart_seq", sequenceName = "cart_cart_id_seq", allocationSize = 1)
    @Column(name = "cart_id")
    private Long cartId;

    @Column(name = "ware_id")
    private Long productId;

    @Column(name = "client_id")
    private Long clientId;

    @Column(name = "ware_cost")
    private Long productCost;

    @Column(name = "ware_amount")
    private Long productAmount;

    @ManyToOne
    @JoinColumn(name = "ware_id", insertable = false, updatable = false)
    private Product product;
}