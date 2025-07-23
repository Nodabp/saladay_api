package com.saladay.saladay_api.domain.entitiy;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menus extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private int price;

    @ManyToOne
    @JoinColumn(name = "categorys_id")
    private Categorys categorys;

    private boolean isActive = true;

    @OneToMany(mappedBy = "menus")
    private List<OrdersItem> ordersItems = new ArrayList<>();
}
