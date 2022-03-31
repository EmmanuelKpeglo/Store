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
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String unit;
    private double price;

    @OneToMany(mappedBy = "product")
    private List<OrderDetail> orderDetails;

}
