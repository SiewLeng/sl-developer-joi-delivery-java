package com.tw.joi.delivery.dto.response;


import com.tw.joi.delivery.domain.GroceryProduct;
import com.tw.joi.delivery.domain.Product;

import java.util.Set;

public record StoreInventory(String outletId, Set<GroceryProduct> products) {
}