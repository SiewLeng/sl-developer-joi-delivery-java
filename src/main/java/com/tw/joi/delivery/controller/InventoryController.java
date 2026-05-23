package com.tw.joi.delivery.controller;

import com.tw.joi.delivery.dto.response.GroceryProductInventoryInfo;
import com.tw.joi.delivery.service.OutletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final OutletService outletService;


    @GetMapping("/health")
    public ResponseEntity<Set<GroceryProductInventoryInfo>> fetchStoreInventoryHealth(@RequestParam(name = "storeId") String storeId) {
        return ResponseEntity.ok(outletService.getInventory(storeId));
    }
}
