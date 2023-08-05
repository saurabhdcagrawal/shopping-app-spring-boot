package com.shopping.inventoryservice.repository;

import com.shopping.inventoryservice.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory,Long> {
    //Optional<Inventory> findBySkuCode(String skuCode);
    //automatically creates
    List<Inventory> findBySkuCodeIn(List<String> skuCode);

}
