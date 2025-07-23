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
public class Categorys {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // ex: 샐러드, 포케, 사이드 등

    private boolean adultOnly;

    @OneToMany(mappedBy = "categorys")
    private List<Menus> menus = new ArrayList<>();
}
