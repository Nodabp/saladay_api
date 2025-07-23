package com.saladay.saladay_api.domain.entitiy;
import com.saladay.saladay_api.domain.enums.PointType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Point extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private Users users;

    private int pointAmount;

    @Enumerated(EnumType.STRING)
    private PointType type; // 적립 or 사용
}