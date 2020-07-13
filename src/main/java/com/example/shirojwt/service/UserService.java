package com.example.shirojwt.service;

import com.example.shirojwt.entity.User;

public interface UserService {
    User findByUsername(String username);
}
