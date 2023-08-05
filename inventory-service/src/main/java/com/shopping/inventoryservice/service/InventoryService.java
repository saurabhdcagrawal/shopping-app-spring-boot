package com.shopping.inventoryservice.service;

import com.shopping.inventoryservice.repository.InventoryRepository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
//automatically create and commit the transactions
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    @Transactional(readOnly=true)
    public boolean isInStock(String skuCode){
        return inventoryRepository.findByskuCode(skuCode).isPresent();
    }
}
