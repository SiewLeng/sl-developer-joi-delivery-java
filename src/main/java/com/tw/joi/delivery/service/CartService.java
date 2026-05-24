package com.tw.joi.delivery.service;

import com.tw.joi.delivery.domain.*;
import com.tw.joi.delivery.dto.request.AddProductRequest;
import com.tw.joi.delivery.dto.response.CartProductInfo;
import com.tw.joi.delivery.seedData.SeedData;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {
    private final Map<String,Cart> userCarts= SeedData.cartForUsers;
    private final UserService userService;

    public CartProductInfo addProductToCartForUser(AddProductRequest addProductRequest) {
        User user=userService.fetchUserById(addProductRequest.getUserId());
        Cart cart = fetchCartForUser(user);
        Outlet outlet = cart.getOutlet();
        if (!(outlet instanceof GroceryStore)) {
            return new CartProductInfo(cart, null, null);
        }
        if (!outlet.getOutletId().equals(addProductRequest.getOutletId())) {
            return new CartProductInfo(cart, null, null);
        }
        boolean isAvailabilityAndReduceStockByOne = ProductService.decrementAvailableStockByOne(
                addProductRequest.getProductId(), addProductRequest.getOutletId());
        if (!isAvailabilityAndReduceStockByOne) {
            return new CartProductInfo(cart, null, null);
        }
        GroceryProduct product = ProductService.getProduct(addProductRequest.getProductId(), addProductRequest.getOutletId());
        cart.getProducts().add(product);
        return new CartProductInfo(cart, product, product.getSellingPrice());
    }

    public Cart getCartForUser(String userId) {
        User user=userService.fetchUserById(userId);
        return fetchCartForUser(user);
    }

    private Cart fetchCartForUser(User user) {
        return userCarts.get(user.getUserId());
    }

}
