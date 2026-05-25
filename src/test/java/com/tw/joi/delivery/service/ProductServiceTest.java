package com.tw.joi.delivery.service;

import com.tw.joi.delivery.domain.GroceryProduct;
import net.bytebuddy.implementation.bytecode.Throw;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProductServiceTest {

    @Autowired
    ProductService productService;

    @Test
    void shouldGetValidProduct() {
        GroceryProduct product = productService.getProduct("product101", "store101");
        Assertions.assertEquals("product101", product.getProductId());
        Assertions.assertEquals("store101", product.getStore().getOutletId());
    }

    @Test
    void shouldGetInValidProduct() {
        GroceryProduct product = productService.getProduct("product101", "store102");
        Assertions.assertNull(product);
    }

    @Test
    void shouldDecrementAvailableStockByOne() {
        // valid product
        String productId = "product101";
        String outletId = "store101";
        GroceryProduct product = productService.getProduct(productId, outletId);
        int initialStockAvailable = product.getAvailableStock();
        for (int i = initialStockAvailable; i > 0; i--) {
            boolean result = productService.decrementAvailableStockByOne(productId, outletId);
            Assertions.assertTrue(result);
        }
        boolean result = productService.decrementAvailableStockByOne(productId, outletId);
        Assertions.assertFalse(result);
    }

    @Test
    void shouldNotDecrementAvailableStockByOne() {
        // invalid product
        String productId = "product101";
        String outletId = "store102";
        boolean result = productService.decrementAvailableStockByOne(productId, outletId);
        Assertions.assertFalse(result);
    }
}
