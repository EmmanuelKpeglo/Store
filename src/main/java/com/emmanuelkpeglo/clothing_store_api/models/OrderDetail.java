package com.emmanuelkpeglo.clothing_store_api.models;

import com.emmanuelkpeglo.clothing_store_api.models.base.BaseEntity;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString

@Entity
@Table(name = "order_detail")
public class OrderDetail extends BaseEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
    private int quantity;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    public OrderDetail(Long id, int quantity, Order order, Product product) {
        super(id);
        this.quantity = quantity;
        this.order = order;
        this.product = product;
    }

    public OrderDetail(Order order, Product product, int quantity) {
        this.quantity = quantity;
        this.order = order;
        this.product = product;
    }
}
