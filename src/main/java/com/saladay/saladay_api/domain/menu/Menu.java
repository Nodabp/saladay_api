package com.saladay.saladay_api.domain.menu;

import com.saladay.saladay_api.domain.common.BaseTimeEntity;
import com.saladay.saladay_api.domain.orders.OrdersItem;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private int price;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private boolean isActive = true;

    @OneToMany(mappedBy = "menu")
    private List<OrdersItem> ordersItems = new ArrayList<>();

    private LocalDateTime visibleFrom;
    private LocalDateTime visibleUntil;

    @Column(name = "image_url")
    private String imageUrl;

}
