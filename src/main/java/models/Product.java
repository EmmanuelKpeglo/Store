package models;

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
    @Column(name = "productId", nullable = false)
    private Long productId;

    private String name;
    private String unit;
    private double price;

    @OneToMany(mappedBy = "product")
    private List<OrderDetail> orderDetails;

}
