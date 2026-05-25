package com.tw.joi.delivery.controller;

import com.tw.joi.delivery.dto.response.StoreInventory;
import com.tw.joi.delivery.service.OutletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final OutletService outletService;

    @GetMapping("/health")
    public ResponseEntity<StoreInventory> fetchStoreInventoryHealth(@RequestParam(name = "storeId") String storeId) {
        return ResponseEntity.ok(outletService.getInventory(storeId));
    }
}
