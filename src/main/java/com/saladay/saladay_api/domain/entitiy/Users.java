package com.saladay.saladay_api.domain.entitiy;

import com.saladay.saladay_api.domain.enums.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Users extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;  // M, F, X

    @Column(nullable = false)
    private LocalDate birth;

    private String address1;
    private String address2;
    private String zipcode;

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<Orders> orders = new ArrayList<>();

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<Point> points = new ArrayList<>();
}
