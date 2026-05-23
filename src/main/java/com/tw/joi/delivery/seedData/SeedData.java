package com.tw.joi.delivery.seedData;

import com.tw.joi.delivery.domain.*;
import com.tw.joi.delivery.dto.response.GroceryProductInventoryInfo;
import com.tw.joi.delivery.service.OutletService;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.util.*;

public class SeedData {
    private static OutletService outletService;

    public static GroceryStore store101 = SeedData.createStore("Fresh Picks", "store101");
    public static GroceryStore store102 = SeedData.createStore("Natural Choice", "store102");
    public static List<GroceryStore> groceryStores = Arrays.asList(store101, store102);

    public static User user101= SeedData.createUser("user101", "John", "Doe");
    public static User user102= SeedData.createUser("user102", "Rachel", "Zane");

    public static Map<String, Cart> cartForUsers = Map.of(
            user101.getUserId(), createCartForUser(user101, "cart101", store101),
            user102.getUserId(), createCartForUser(user102, "cart102", store102));

    public static List<GroceryProduct> groceryProducts =
        Arrays.asList(createGroceryProduct("Wheat Bread", "product101", store101),
                      createGroceryProduct("Spinach", "product102", store101),
                      createGroceryProduct("Crackers", "product103", store101),
                      createGroceryProduct("Cow UTH Milk", "product104", store102));

    public static List<User> users = Arrays.asList(user101, user102);

    public static Cart createCartForUser(User user, String cartId, Outlet outlet) {
        return Cart.builder()
            .cartId(cartId)
            .outlet(outlet)
            .user(user)
            .build();
    }

    public static GroceryStore createStore(String outletName, String storeId) {
        return GroceryStore.builder()
            .name(outletName)
            .outletId(storeId)
            .build();
    }

    public static User createUser(String userId, String firstName, String lastName) {
        return User.builder()
            .userId(userId)
            .firstName(firstName)
            .lastName(lastName)
            .email(firstName + "." + lastName + "@gmail.com")
            .phoneNumber(String.valueOf(SeedData.getRandomNumberUsingNextInt(100000000, 900000000)))
            .build();
    }

    public static int getRandomNumberUsingNextInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    private static GroceryProduct createGroceryProduct(String productName,
                                                       String productId, GroceryStore store) {
        return GroceryProduct.builder()
            .productName(productName)
            .productId(productId)
            .mrp(BigDecimal.valueOf(10.5))
            .weight(BigDecimal.valueOf(500.00))
            .store(store)
            .threshold(10)
            .availableStock(3)
            .build();
    }

}
