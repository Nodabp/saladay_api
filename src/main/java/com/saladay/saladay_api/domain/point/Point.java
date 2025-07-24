package com.saladay.saladay_api.domain.point;
import com.saladay.saladay_api.domain.common.BaseTimeEntity;
import com.saladay.saladay_api.domain.enums.PointSource;
import com.saladay.saladay_api.domain.enums.PointType;
import com.saladay.saladay_api.domain.users.Users;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Point extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private Users users;

    private int pointAmount;

    @Enumerated(EnumType.STRING)
    private PointType type; // 적립 or 사용

    private String description;

    private LocalDateTime expiredAt;

    @Enumerated(EnumType.STRING)
    private PointSource source; // SYSTEM / ADMIN / REFERRAL

    private Long relatedOrderId; // 주문 번호와 연결된 포인트일 경우
}