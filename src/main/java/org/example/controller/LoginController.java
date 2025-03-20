package org.example.controller;

import org.example.pojo.Result;
import org.example.pojo.User;
import org.example.service.impl.LoginServiceImpl;
import org.example.service.impl.UserServiceImpl;
import org.example.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {
    @Autowired
    private LoginServiceImpl loginServiceImpl;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    RedisTemplate<String, User> redisTemplate;

    @PostMapping("/api/login")
    public Result login(@RequestBody User user) {
        boolean check = loginServiceImpl.login(user.getUsername(), user.getPassword());
        if (check) {
            Map<String, Object> map = new HashMap<>();
            map.put("username", user.getUsername());
            return Result.success(JwtUtils.generateJwt(map));
        } else {
            return Result.error("用户名或密码错误");
        }
    }
}
