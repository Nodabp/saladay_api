package com.saladay.saladay_api.repository;

import com.saladay.saladay_api.domain.discount.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface DiscountRepository extends JpaRepository<Discount, Long> {
    @Query("SELECT d FROM Discount d WHERE d.isActive = true AND :now BETWEEN d.startTime AND d.endTime")
    List<Discount> findAllActiveBetween(@Param("now") LocalDateTime now); // 정렬용
}
