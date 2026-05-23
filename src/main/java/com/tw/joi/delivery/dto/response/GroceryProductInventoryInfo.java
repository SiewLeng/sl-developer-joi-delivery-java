package com.tw.joi.delivery.dto.response;


public record GroceryProductInventoryInfo(String productId, String productName, int availableStock, String outletId) {
}