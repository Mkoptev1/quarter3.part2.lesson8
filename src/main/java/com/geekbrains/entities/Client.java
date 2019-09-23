package com.geekbrains.entities;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "clients")

public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_seq")
    @SequenceGenerator(name = "client_seq", sequenceName = "clients_client_id_seq", allocationSize = 1)
    @Column(name = "client_id")
    private Long id;

    @Column(name = "client_name")
    private String name;

    @OneToMany
    @JoinTable(
            name = "cart",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "ware_id")
    )
    private List<Product> productList;
}