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
@Table(name = "shipper")
public class Shipper extends BaseEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;

    private String name;
    private String phone;

    public Shipper(Long id, String name, String phone) {
        super(id);
        this.name = name;
        this.phone = phone;
    }

    @OneToMany(mappedBy = "shipper")
    private List<Order> orders;

}
