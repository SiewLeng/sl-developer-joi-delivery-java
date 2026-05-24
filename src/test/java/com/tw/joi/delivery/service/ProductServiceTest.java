package com.tw.joi.delivery.service;

import com.tw.joi.delivery.domain.GroceryProduct;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProductServiceTest {

    @Test
    void shouldGetValidProduct() {
        GroceryProduct product = ProductService.getProduct("product101", "store101");
        Assertions.assertEquals("product101", product.getProductId());
        Assertions.assertEquals("store101", product.getStore().getOutletId());
    }

    @Test
    void shouldGetInValidProduct() {
        GroceryProduct product = ProductService.getProduct("product101", "store102");
        Assertions.assertNull(product);
    }

    @Test
    void shouldDecrementAvailableStockByOne() {
        // valid product
        String productId = "product101";
        String outletId = "store101";
        GroceryProduct product = ProductService.getProduct(productId, outletId);
        boolean result = ProductService.decrementAvailableStockByOne(productId, outletId);
        if (product.getAvailableStock() >= 0) {
            Assertions.assertTrue(result);
        } else {
            Assertions.assertFalse(result);
        }
    }

    @Test
    void shouldNotDecrementAvailableStockByOne() {
        // invalid product
        String productId = "product101";
        String outletId = "store102";
        boolean result = ProductService.decrementAvailableStockByOne(productId, outletId);
        Assertions.assertFalse(result);
    }
}
