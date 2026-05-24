package com.tw.joi.delivery.service;

import com.tw.joi.delivery.domain.GroceryProduct;
import com.tw.joi.delivery.domain.Product;
import com.tw.joi.delivery.dto.response.StoreInventory;
import com.tw.joi.delivery.seedData.SeedData;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class OutletService {

    static List<GroceryProduct> groceryProducts = SeedData.groceryProducts;

    public StoreInventory getInventory(String outletId) {
        Set<GroceryProduct> products = new HashSet<>();
        for (GroceryProduct groceryProduct: groceryProducts) {
            if (groceryProduct.getStore().getOutletId().equals(outletId)) {
                products.add(groceryProduct);
            }
        }
        return new StoreInventory(outletId, products);
    }

}
