package org.example.controller;

import org.example.pojo.Result;
import org.example.pojo.User;
import org.example.service.impl.RegisterServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {
    @Autowired
    private RegisterServiceImpl registerServiceImpl;

    @PostMapping("/api/register")
    public Result register(@RequestBody User user) {
        registerServiceImpl.register(user.getUsername(),user.getPassword());
        return Result.success();
    }
}
