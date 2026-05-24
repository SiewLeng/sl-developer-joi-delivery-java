package com.tw.joi.delivery.domain;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Resturant extends Outlet {

    private Set<FoodProduct> inventory=new HashSet<>();

    @Builder
    public Resturant(String name, String description, String outletId) {
        super(name, description, outletId);
        this.inventory = new HashSet<>();
    }

}
