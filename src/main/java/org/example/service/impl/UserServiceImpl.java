package org.example.service.impl;

import org.example.mapper.UserMapper;
import org.example.pojo.User;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    public List<User> listUser() {
        return userMapper.selectUser("Alice");
    }

    public String home(String username) {
        return userMapper.getAvatar(username);
    }

    public User personalhome(String username) {
        return userMapper.getUser(username);
    }

    public void update(String username, String newUsername, String newPassword, String newPhone, String newAvatarUrl) {
        userMapper.updateUser(username, newUsername, newPassword, newPhone, newAvatarUrl);
    }
}
