package com.saladay.saladay_api.domain.menu;

import com.saladay.saladay_api.domain.enums.OptionType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MenuOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    private String name; // 예: "치즈 추가"

    private int extraPrice; // 옵션 추가 금액

    @Enumerated(EnumType.STRING)
    private OptionType type; // TOPPING / SIZE / EXTRA

    private boolean isDefault; // 기본 선택 여부

    private boolean isRequired; // 필수 선택 여부

    private int displayOrder; // UI 노출 순서

    @Column(name = "image_url")
    private String imageUrl;
}
