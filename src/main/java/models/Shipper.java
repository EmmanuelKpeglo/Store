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
@Table(name = "shipper")
public class Shipper {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "shipperId", nullable = false)
    private Long shipperId;

    private String name;
    private int phone;

    @OneToMany(mappedBy = "shipper")
    private List<Order> orders;

}
