package com.example.shirojwt.controller;

import com.example.shirojwt.entity.User;
import com.example.shirojwt.service.UserService;
import com.example.shirojwt.util.JwtUtil;
import com.example.shirojwt.util.R;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SysController {

    @Autowired
    private UserService userService;

    @PostMapping("login")
    public R login(String username, String password) {

        User user = userService.findByUsername(username);

        if (user == null) {
            return R.error("用户名不存在");
        }
        if (user.getPassword().equals(password)) {
            String token = JwtUtil.signToken(user.getUsername(), user.getId());
            return R.ok("登录成功").put("token", token);
        }

        return R.error("用户名密码不对");
    }



    @RequestMapping("test")
    public R test() {
        Object user = SecurityUtils.getSubject().getPrincipal();
        return R.ok().put("user", user);
    }


    @RequestMapping("roleinfo")
    @RequiresRoles("admin")
    public R roleinfo() {
        Object user = SecurityUtils.getSubject().getPrincipal();
        return R.ok().put("user", user);
    }

   
    
    
}




