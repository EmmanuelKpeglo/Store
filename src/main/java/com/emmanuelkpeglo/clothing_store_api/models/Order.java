package com.emmanuelkpeglo.clothing_store_api.models;

import com.emmanuelkpeglo.clothing_store_api.models.base.BaseEntity;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString

@Entity
@Table(name = "_order")
public class Order extends BaseEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
    @CreatedDate
    private LocalDateTime date;

    public Order(Long id, Customer customer) {
        super(id);
        this.customer = customer;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetails;

    public Order(Customer customer) {
        this.customer = customer;
    }
}
