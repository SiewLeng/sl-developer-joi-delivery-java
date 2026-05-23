package com.tw.joi.delivery.domain;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.tw.joi.delivery.dto.response.GroceryProductInventoryInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GroceryStore extends Outlet {

    private Set<GroceryProductInventoryInfo> inventory=new HashSet<>();

    @Builder
    public GroceryStore(String name, String description, String outletId) {
        super(name, description, outletId);
        this.inventory = new HashSet<>();
    }
}
