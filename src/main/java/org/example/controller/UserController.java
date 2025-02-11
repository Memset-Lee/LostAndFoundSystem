package org.example.controller;

import io.jsonwebtoken.Claims;
import org.example.pojo.Result;
import org.example.pojo.User;
import org.example.service.impl.UserServiceImpl;
import org.example.utils.AliOSSUtils;
import org.example.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private AliOSSUtils aliOSSUtils;

    @RequestMapping("/test")
    public Result listUser() {
        List<User> ansList = userServiceImpl.listUser();
        return Result.success(ansList);
    }

    @GetMapping("/api/home")
    public Result home(@RequestHeader String token) {
        Claims claims = JwtUtils.parseJwt(token);
        String username = claims.get("username").toString();
        String avatar = userServiceImpl.home(username);
        return Result.success(avatar);
    }

    @GetMapping("/api/personalhome")
    public Result personalhome(@RequestHeader String token) {
        Claims claims = JwtUtils.parseJwt(token);
        String username = claims.get("username").toString();
        User user = userServiceImpl.personalhome(username);
        return Result.success(user);
    }

    @PutMapping("/api/update")
    public Result update(@RequestHeader String token,
                         @RequestPart("username") String newUsername,
                         @RequestPart("password") String newPassword,
                         @RequestPart("phone") String newPhone,
                         @RequestPart("avatar") MultipartFile newAvatar) throws IOException {
        Claims claims = JwtUtils.parseJwt(token);
        String username = claims.get("username").toString();
        String newAvatarUrl = aliOSSUtils.upload(newAvatar);
        userServiceImpl.update(username, newUsername, newPassword, newPhone, newAvatarUrl);
        Map<String, Object> map = new HashMap<>();
        map.put("username", newUsername);
        return Result.success(JwtUtils.generateJwt(map));
    }
}
