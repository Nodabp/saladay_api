package com.saladay.saladay_api.repository;

import com.saladay.saladay_api.domain.menu.Menu;
import com.saladay.saladay_api.domain.menu.MenuInventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuInventoryRepository extends JpaRepository<MenuInventory, Long> {
    MenuInventory findByMenuId(Long menuId);

    MenuInventory findByMenu(Menu menu);
}
