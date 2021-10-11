package ru.geekbrains.summer.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Data
@Entity
@Table(name = "orders")
//@NamedEntityGraph(
//        name = "orders.for-front",
//        attributeNodes = {
//                @NamedAttributeNode(value = "items", subgraph = "items-products")
//        },
//        subgraphs = {
//                @NamedSubgraph(
//                        name = "items-products",
//                        attributeNodes = {
//                                @NamedAttributeNode("product")
//                        }
//                )
//        }
//)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items;

    @Column(name = "price")
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "status")
    private String status;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}

