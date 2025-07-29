package com.saladay.saladay_api.repository;

import com.saladay.saladay_api.domain.menu.Menu;
import com.saladay.saladay_api.domain.menu.MenuOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuOptionRepository extends JpaRepository<MenuOption, Long> {
    List<MenuOption> findAllByMenuId(Long menuId);

    List<MenuOption> findByMenu(Menu menu);
}
