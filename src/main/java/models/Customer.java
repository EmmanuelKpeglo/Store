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
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "customerId", nullable = false)
    private Long customerId;

    private String Name;
    private String address;
    private String city;
    private String postalCode;
    private String Country;

    @OneToMany(mappedBy = "customer")
    private List<Order> orders;
}
