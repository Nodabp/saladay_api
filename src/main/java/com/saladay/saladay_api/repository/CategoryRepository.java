package com.saladay.saladay_api.repository;

import com.saladay.saladay_api.domain.menu.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
