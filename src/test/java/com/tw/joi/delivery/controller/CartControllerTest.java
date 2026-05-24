package com.tw.joi.delivery.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.tw.joi.delivery.domain.*;
import com.tw.joi.delivery.dto.request.AddProductRequest;
import com.tw.joi.delivery.dto.response.CartProductInfo;
import com.tw.joi.delivery.service.CartService;
import com.tw.joi.delivery.service.ProductService;
import com.tw.joi.delivery.service.UserService;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.ArrayList;

@WebMvcTest(CartController.class)
public class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CartService cartService;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private ProductService productService;

    ObjectMapper mapper = new ObjectMapper();

    @Test
    void shouldAddTheRequestedProductToTheCart() throws Exception {

        String url = "/cart/product";
        AddProductRequest addProductRequest = new AddProductRequest();
        addProductRequest.setProductId("product101");
        addProductRequest.setUserId("user101");
        addProductRequest.setOutletId("store101");

        GroceryStore outlet = GroceryStore.builder()
                .name("Fresh Picks")
                .outletId("store101")
                .build();

        User user = User.builder()
                .userId("user101")
                .firstName("John")
                .lastName("Zoe")
                .build();

        Cart cart = Cart.builder()
                .cartId("cart101")
                .user(user)
                .outlet(outlet)
                .products(new ArrayList<>())
                .build();

        GroceryProduct groceryProduct = GroceryProduct.builder()
                .productName("Wheat Bread")
                .productId("product101")
                .mrp(BigDecimal.valueOf(10.5))
                .weight(BigDecimal.valueOf(500.00))
                .store(outlet)
                .threshold(10)
                .availableStock(3)
                .build();

        CartProductInfo cartProductInfo = new CartProductInfo(cart, groceryProduct, BigDecimal.valueOf(1.00));
        when(cartService.addProductToCartForUser(any(AddProductRequest.class))).thenReturn(cartProductInfo);

        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(addProductRequest);

        mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cart.cartId").value("cart101"))
                .andExpect(jsonPath("$.cart.outlet.outletId").value("store101"))
                .andExpect(jsonPath("$.product.productId").value("product101"))
                .andDo(print());
    }

    @Test
    void shouldReturnTheCart() throws Exception {
        String url = "/cart/view?userId={userId}";
        String userId = "user101";
        Cart cart= Cart.builder()
            .cartId("cart101")
            .build();
        when(cartService.getCartForUser(userId)).thenReturn(cart);

        mockMvc.perform(MockMvcRequestBuilders.get(url,"user101")
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.cartId", Is.is("cart101")))
                .andDo(print());
    }
}