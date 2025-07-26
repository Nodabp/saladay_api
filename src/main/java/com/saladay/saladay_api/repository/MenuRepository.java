package com.saladay.saladay_api.repository;

import com.saladay.saladay_api.domain.menu.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long>{
    List<Menu> findByIsActiveTrue();
    List<Menu> findByCategoryId(Long categoryId);
}