package com.emmanuelkpeglo.clothing_store_api.models;

import lombok.*;

import javax.persistence.*;
import java.util.List;


@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String address;
    private String city;
    private String postalCode;

    public Customer(Long id, String name, String address, String city, String postalCode, String country) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.postalCode = postalCode;
        Country = country;
    }

    private String Country;

    @OneToMany(mappedBy = "customer")
    private List<Order> orders;
}
