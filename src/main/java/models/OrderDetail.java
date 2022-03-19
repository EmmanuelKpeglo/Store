package models;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString

@Entity
@Table(name = "product")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "orderDetailId", nullable = false)
    private Long orderDetailId;
    private int quantity;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="orderId", referencedColumnName = "orderId")
    private Order order;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="productId", referencedColumnName = "productId")
    private Product product;

}
