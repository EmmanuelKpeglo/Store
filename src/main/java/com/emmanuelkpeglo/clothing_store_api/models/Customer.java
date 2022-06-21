package com.emmanuelkpeglo.clothing_store_api.models;

import com.emmanuelkpeglo.clothing_store_api.models.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;


@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "customer")
public class Customer extends BaseEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;

    private String name;
    private String address;
    private String city;
    private String postalCode;
    private String country;

    public Customer(Long id, String name, String address, String city, String postalCode, String country) {
        super(id);
        this.name = name;
        this.address = address;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
    }

    @OneToMany(mappedBy = "customer")
    private List<Order> orders;
}
