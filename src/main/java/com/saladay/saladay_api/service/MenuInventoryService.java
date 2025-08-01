package com.saladay.saladay_api.service;


import com.saladay.saladay_api.domain.menu.MenuInventory;
import com.saladay.saladay_api.dto.menuDTO.MenuInventoryDTO;
import com.saladay.saladay_api.repository.MenuInventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class MenuInventoryService {

    private final MenuInventoryRepository menuInventoryRepository;
    private final ModelMapper modelMapper;

    public MenuInventoryDTO findByMenuId(Long menuId) {
        return modelMapper.map(menuInventoryRepository.findByMenuId(menuId), MenuInventoryDTO.class);
    }

    public void updateInventory(MenuInventoryDTO menuInventoryDTO) {
        MenuInventory menuInventory = modelMapper.map(menuInventoryDTO, MenuInventory.class);
        menuInventoryRepository.save(menuInventory);
        log.info("Menu Inventory Updated : {}", menuInventory);
    }

    public MenuInventoryDTO updateInventoryByMenuId(Long menuId, int quantityToUse) {
        MenuInventoryDTO menuInventoryDTO = findByMenuId(menuId);

        if (menuInventoryDTO != null) {
            int stock = menuInventoryDTO.getStockQuantity();

            stock = quantityToUse > stock ? 0 : stock - quantityToUse;

            menuInventoryDTO.setStockQuantity(stock);

            updateInventory(menuInventoryDTO);
            log.info("Menu Inventory Updated : {}", menuInventoryDTO);
            return menuInventoryDTO;
        }

        return null;
    }

}
