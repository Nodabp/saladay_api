package com.saladay.saladay_api.repository;

import com.saladay.saladay_api.domain.menu.MenuOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuOptionRepository extends JpaRepository<MenuOption, Long> {
}
