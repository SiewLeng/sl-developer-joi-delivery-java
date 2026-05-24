package com.tw.joi.delivery.domain;

import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FoodProduct extends Product {

    private BigDecimal sellingPrice;
    private BigDecimal discount;
    private boolean availableStock;
    private Resturant store;

    @Builder
    public FoodProduct(String productId, String productName, BigDecimal mrp,
                       BigDecimal sellingPrice, boolean availableStock,
                       Resturant store, BigDecimal discount) {
        super(productId, productName,  mrp);
        this.sellingPrice = sellingPrice;
        this.availableStock = availableStock;
        this.store = store;
        this.discount = discount;
    }

}
