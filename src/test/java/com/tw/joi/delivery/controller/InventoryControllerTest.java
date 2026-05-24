package com.tw.joi.delivery.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.tw.joi.delivery.domain.GroceryProduct;
import com.tw.joi.delivery.domain.GroceryStore;
import com.tw.joi.delivery.domain.Product;
import com.tw.joi.delivery.dto.response.StoreInventory;
import com.tw.joi.delivery.service.OutletService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@WebMvcTest(InventoryController.class)
class InventoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OutletService outletService;

    @Test
    void shouldReturnTheHealthOfTheStore() throws Exception {
        String getUrl = "/inventory/health?storeId={storeId}";
        //add required mocking.
        String storeId = "store101";
        String outletName = "Fresh Picks";
        GroceryStore store101 = GroceryStore.builder()
                .name(outletName)
                .outletId(storeId)
                .build();
        GroceryProduct bread = GroceryProduct.builder()
                .productName("Wheat Bread")
                .productId("product101")
                .mrp(BigDecimal.valueOf(10.5))
                .weight(BigDecimal.valueOf(500.00))
                .store(store101)
                .threshold(10)
                .availableStock(3)
                .build();
        Set<GroceryProduct> products = new HashSet<>();
        products.add(bread);
        when(outletService.getInventory(storeId)).thenReturn(new StoreInventory(storeId, products));

        mockMvc.perform(MockMvcRequestBuilders.get(getUrl,"store101")
                        .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
                //put meaning assertions
                .andExpect(jsonPath("$.outletId").value(storeId))
                .andExpect(jsonPath("$.products", hasSize(1)))
                .andExpect(jsonPath("$.products[0].productId").value("product101"))
                .andExpect(jsonPath("$.products[0].store.outletId").value(storeId))
                .andDo(print());
    }
}