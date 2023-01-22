package com.achyuthreddyi.inventoryservice.repository;

import com.achyuthreddyi.inventoryservice.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    List<Inventory> findBySkuCodeIn(List<String> skuCode);
//    Optional<Inventory> findBySkuCode();
}
