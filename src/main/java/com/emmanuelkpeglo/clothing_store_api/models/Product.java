package com.emmanuelkpeglo.clothing_store_api.models;

import com.emmanuelkpeglo.clothing_store_api.models.base.BaseEntity;
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
public class Product extends BaseEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;

    private String name;
    private String unit;
    private double price;

    @OneToMany(mappedBy = "product")
    private List<OrderDetail> orderDetails;

    public Product(Long id, String name, String unit, double price) {
        super(id);
        this.name = name;
        this.unit = unit;
        this.price = price;
    }
}
