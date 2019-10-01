package com.vkopendoh.rssapp.service;

import com.vkopendoh.rssapp.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql({"/data.sql"})
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    public void getUserFromRepo() {
        User user = new User("User");
        user.setId(1L);
        User fromDb = userService.getUserById(user.getId());
        Assert.assertTrue(user.getUsername().equals(fromDb.getUsername()));
    }

    @Test
    public void addUser() {
        User user = new User("Test");
        userService.addUser(user);
        User dbUser = userService.getUserById(user.getId());
        Assert.assertTrue(dbUser.getUsername().equals(user.getUsername()));
    }
}