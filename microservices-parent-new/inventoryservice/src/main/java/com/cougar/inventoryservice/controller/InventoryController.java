package com.cougar.inventoryservice.controller;

import com.cougar.inventoryservice.dto.InventoryResponse;
import com.cougar.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCodes) // take list skuCodes as request parameter
    {
        return inventoryService.isInStock(skuCodes);
    }
}
