package com.tw.joi.delivery.service;

import com.tw.joi.delivery.domain.Cart;
import com.tw.joi.delivery.domain.GroceryProduct;
import com.tw.joi.delivery.domain.Product;
import com.tw.joi.delivery.dto.request.AddProductRequest;
import com.tw.joi.delivery.dto.response.CartProductInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CartServiceTest {

    @Autowired ProductService productService;
    @Autowired CartService cartService;

    @Test
    void shouldGetCartForUser() {
        String userId = "user101";
        Cart cart=cartService.getCartForUser(userId);
        Assertions.assertNotNull(cart);
        Assertions.assertEquals(userId, cart.getUser().getUserId());
    }

    @Test
    void shouldNotGetCartForUser() {
        String userId = "user103";
        Cart cart=cartService.getCartForUser(userId);
        Assertions.assertNull(cart);
    }

    @Test
    void shouldAddProductToCartForUser() {
        String userId = "user101";
        String productId = "product101";
        String storeId = "store101";

        AddProductRequest addProductRequest = new AddProductRequest();
        addProductRequest.setUserId(userId);
        addProductRequest.setProductId(productId);
        addProductRequest.setOutletId(storeId);

        int initialStockAvailability = productService.getProduct(
                addProductRequest.getProductId(), addProductRequest.getOutletId())
                .getAvailableStock();

        int cartProductSize = cartService.getCartForUser(userId).getProducts().size();
        Assertions.assertEquals(0, cartProductSize);

        for (int i = initialStockAvailability; i > 0; i--) {
            CartProductInfo cartProductInfo = cartService.addProductToCartForUser(addProductRequest);
            Assertions.assertNotNull(cartProductInfo.product());
            List<Product> products = cartProductInfo.cart().getProducts();
            cartProductSize += 1;
            Assertions.assertEquals(cartProductSize, products.size());
            for (Product value : products) {
                Assertions.assertEquals(productId, value.getProductId());
            }
        }
        CartProductInfo cartProductInfo = cartService.addProductToCartForUser(addProductRequest);
        Assertions.assertNull(cartProductInfo.product());
    }
}
