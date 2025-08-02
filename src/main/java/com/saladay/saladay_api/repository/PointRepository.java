package com.saladay.saladay_api.repository;

import com.saladay.saladay_api.domain.menu.Menu;
import com.saladay.saladay_api.domain.point.Point;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PointRepository extends JpaRepository<Point, Long>{
    List<Point> findByUsersIdOrderByCreatedAtDesc(Long userId);
    List<Point> findByUsersId(Long userId);

    List<Point> findByRelatedOrderId(Long id);
}