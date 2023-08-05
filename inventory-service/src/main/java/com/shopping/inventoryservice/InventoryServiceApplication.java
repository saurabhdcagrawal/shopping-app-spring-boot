package com.shopping.inventoryservice;

import com.shopping.inventoryservice.model.Inventory;
import com.shopping.inventoryservice.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {

		SpringApplication.run(InventoryServiceApplication.class, args);
	}
	@Bean
	CommandLineRunner commandLineRunner(InventoryRepository inventoryRepository){
		return args -> {
			Inventory inventory= new Inventory();
			inventory.setSkuCode("iphone_13");
			inventory.setQuantity(100);
			Inventory inventory1= new Inventory();
			inventory1.setSkuCode("iphone_13_red");
			inventory1.setQuantity(0);
			inventoryRepository.save(inventory);
			inventoryRepository.save(inventory1);
		};

	}
}
