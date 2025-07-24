package com.saladay.saladay_api.domain.menu;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // ex: 샐러드, 포케, 사이드 등

    private boolean adultOnly;

    @Column(nullable = true)
    private String description; // 마케팅 설명용

    private int sortOrder; // UI 정렬용

    private String iconPath; // 카테고리 아이콘 이미지 경로

    @OneToMany(mappedBy = "category")
    private List<Menu> menu = new ArrayList<>();
}