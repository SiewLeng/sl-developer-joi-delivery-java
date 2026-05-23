package com.tw.joi.delivery.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.tw.joi.delivery.dto.response.GroceryProductInventoryInfo;
import com.tw.joi.delivery.service.CartService;
import com.tw.joi.delivery.service.OutletService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

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
        String storeId = "store101";
        //add required mocking.
        Set<GroceryProductInventoryInfo> inventory = new HashSet<>();
        inventory.add(new GroceryProductInventoryInfo(
                "product101", "Wheat Bread", 2, "store101"));
        when(outletService.getInventory(storeId)).thenReturn(inventory);

        mockMvc.perform(MockMvcRequestBuilders.get(getUrl,"store101")
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
                //put meaning assertions
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].productId").value("product101"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].productName").value("Wheat Bread"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].availableStock").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].outletId").value("store101"))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)));
    }
}