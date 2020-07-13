package com.example.shirojwt.service;

import com.example.shirojwt.entity.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    
    private static List<User> users = new ArrayList<User>() {
        {
            this.add(new User(1L, "admin", "123456", "admin"));
            this.add(new User(2L, "lzj", "lzj", "user"));
        }
    };

    @Override
    public User findByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
}
