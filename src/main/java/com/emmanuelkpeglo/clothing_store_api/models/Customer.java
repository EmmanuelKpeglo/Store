package com.emmanuelkpeglo.clothing_store_api.models;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString

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
    private String Country;

    @OneToMany(mappedBy = "customer")
    private List<Order> orders;
}
