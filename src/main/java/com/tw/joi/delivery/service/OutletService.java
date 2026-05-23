package com.tw.joi.delivery.service;

import com.tw.joi.delivery.domain.GroceryProduct;
import com.tw.joi.delivery.dto.response.GroceryProductInventoryInfo;
import com.tw.joi.delivery.seedData.SeedData;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class OutletService {

    static List<GroceryProduct> groceryProducts = SeedData.groceryProducts;

    public static Set<GroceryProductInventoryInfo> getInventory(String outletId) {
        Set<GroceryProductInventoryInfo> inventory = new HashSet<>();
        for (GroceryProduct groceryProduct: groceryProducts) {
            if (groceryProduct.getStore().getOutletId().equals(outletId)) {
                String productId = groceryProduct.getProductId();
                String productName = groceryProduct.getProductName();
                int availableStock = groceryProduct.getAvailableStock();
                String productOutletId = groceryProduct.getStore().getOutletId();
                inventory.add(new GroceryProductInventoryInfo(
                        productId, productName,
                        availableStock, productOutletId));
            }
        }
        return inventory;
    }

}
