package com.saladay.saladay_api.util.mapper;

import com.saladay.saladay_api.domain.menu.Menu;
import com.saladay.saladay_api.domain.menu.MenuInventory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AvailabilityMapper {

    public boolean isMenuAvailable(Menu menu, MenuInventory inventory) {
        LocalDateTime now = LocalDateTime.now();

        boolean isAfterVisibleFrom = menu.getVisibleFrom() == null || menu.getVisibleFrom().isBefore(now);
        boolean isBeforeVisibleUntil = menu.getVisibleUntil() == null || now.isBefore(menu.getVisibleUntil());

        boolean isStockAvailable = inventory.getStockQuantity() > 0 && !inventory.isSoldOutManually();

        return menu.isActive() && isAfterVisibleFrom && isBeforeVisibleUntil && isStockAvailable;
    }
}
