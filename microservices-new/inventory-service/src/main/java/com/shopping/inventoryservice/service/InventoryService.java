package com.shopping.inventoryservice.service;

import com.shopping.inventoryservice.dto.InventoryResponse;
import com.shopping.inventoryservice.model.Inventory;
import com.shopping.inventoryservice.repository.InventoryRepository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
//automatically create and commit the transactions
@Slf4j
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    @Transactional(readOnly=true)
    //@SneakyThrows
    public List<InventoryResponse> isInStock(List<String> skuCode){
       /* For resilience4j simulation timeout
       log.info("Wait started");
        Thread.sleep(10000);
        log.info("Wait ended");*/
        return inventoryRepository.findBySkuCodeIn(skuCode).stream().map(this::convertToInventoryResponseDto).collect(Collectors.toList());
    }
    public InventoryResponse convertToInventoryResponseDto(Inventory inventory){
        InventoryResponse inventoryResponse= InventoryResponse.builder().skuCode(inventory.getSkuCode())
                .isInStock(inventory.getQuantity()>0).build();
        return inventoryResponse;
    }
}
