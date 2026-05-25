package com.tw.joi.delivery.service;

import com.tw.joi.delivery.domain.GroceryProduct;
import com.tw.joi.delivery.seedData.SeedData;
import java.util.List;

import org.springframework.stereotype.Service;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class ProductService {
    private final Lock lock = new ReentrantLock();
    private final List<GroceryProduct> products= SeedData.groceryProducts;

    public GroceryProduct getProduct(String productId, String outletId) {
        return products.stream()
            .filter(groceryProduct ->
                        groceryProduct.getProductId().equals(productId)
                            && groceryProduct.getStore().getOutletId().equals(outletId))
            .findFirst()
            .orElse(null);
    }

    public boolean decrementAvailableStockByOne(String productId, String outletId) {
        GroceryProduct product = getProduct(productId, outletId);
        if (product == null) return false;
        int availableStock = 0;
        lock.lock();
        try {
            availableStock = product.getAvailableStock();
            if (availableStock >= 1) {
                product.setAvailableStock(availableStock - 1);
            }
        } finally {
            lock.unlock();
        }
        return availableStock >= 1;
    }

}
