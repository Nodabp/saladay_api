package com.saladay.saladay_api.repository;

import com.saladay.saladay_api.domain.menu.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Long>{
    List<Menu> findByIsActiveTrue();
    List<Menu> findByCategoryId(Long categoryId);
    Optional<Menu> findById(Long id); // 정상 작동
}