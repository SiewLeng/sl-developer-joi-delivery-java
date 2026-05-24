package com.tw.joi.delivery.service;

import com.tw.joi.delivery.dto.response.StoreInventory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OutletServiceTest {

    @Autowired OutletService outletService;

    @Test
    void shouldReturnInventory() {
        String outletId = "store101";
        StoreInventory inventory = outletService.getInventory(outletId);
        Assertions.assertEquals(outletId, inventory.outletId());
        Assertions.assertEquals(3, inventory.products().size());
        inventory.products().forEach(
                product -> Assertions.assertEquals(outletId, product.getStore().getOutletId()));
    }

    @Test
    void shouldReturnEmptyInventory() {
        String outletId = "store103";
        StoreInventory inventory = outletService.getInventory(outletId);
        Assertions.assertEquals(outletId, inventory.outletId());
        Assertions.assertEquals(0, inventory.products().size());
    }
}
