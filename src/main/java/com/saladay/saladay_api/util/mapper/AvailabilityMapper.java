package com.saladay.saladay_api.util.mapper;

import com.saladay.saladay_api.domain.menu.Menu;
import com.saladay.saladay_api.domain.menu.MenuInventory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AvailabilityMapper {
    /* 메뉴에서 활성화된 메뉴만 가져올 수 있도록 따로 설정된 매핑 컴포넌트 */
    public boolean isMenuAvailable(Menu menu, MenuInventory inventory) {
        if(inventory == null) return false; // 나중에 false 로 바꾸는거 잊지말기
        LocalDateTime now = LocalDateTime.now();

        boolean isAfterVisibleFrom = menu.getVisibleFrom() == null || menu.getVisibleFrom().isBefore(now);
        boolean isBeforeVisibleUntil = menu.getVisibleUntil() == null || now.isBefore(menu.getVisibleUntil());
        boolean isStockAvailable = inventory.getStockQuantity() > 0 && !inventory.isSoldOutManually();

        return menu.isActive() && isAfterVisibleFrom && isBeforeVisibleUntil && isStockAvailable;
    }
}
