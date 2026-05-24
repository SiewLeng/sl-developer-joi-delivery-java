package com.tw.joi.delivery.service;

import com.tw.joi.delivery.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    void shouldGetValidUser() {
        User user = userService.fetchUserById("user101");
        Assertions.assertEquals("user101", user.getUserId());
    }

    @Test
    void shouldGetInValidUser() {
        User user = userService.fetchUserById("user103");
        Assertions.assertNull(user);
    }
}
