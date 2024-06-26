package com.cougar.inventoryservice;

import com.cougar.inventoryservice.model.Inventory;
import com.cougar.inventoryservice.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryserviceApplication.class, args);
	}

	// Creating some data to the database at the time of app start up

	@Bean
	public CommandLineRunner loadData(InventoryRepository inventoryRepository)
	{
		if(inventoryRepository.findAll().size() >0)
		{
			return args -> {};
		}
		return args -> {
			Inventory inventory1 = new Inventory();
			inventory1.setSkuCode("iphone_13");
			inventory1.setQuantity(100);

			Inventory inventory2 = new Inventory();
			inventory2.setSkuCode("iphone_13_red");
			inventory2.setQuantity(0);

			Inventory inventory3 = new Inventory();
			inventory3.setSkuCode("iphone_14_black");
			inventory3.setQuantity(10);

			Inventory inventory4 = new Inventory();
			inventory4.setSkuCode("iphone_16_gold");
			inventory4.setQuantity(2);

			inventoryRepository.save(inventory1);
			inventoryRepository.save(inventory2);
			inventoryRepository.save(inventory3);
			inventoryRepository.save(inventory4);
		};
	}

}
