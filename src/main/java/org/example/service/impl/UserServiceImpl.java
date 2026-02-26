package org.example.service.impl;

import org.example.mapper.UserMapper;
import org.example.pojo.User;
import org.example.service.UserService;
import org.example.utils.AliOSSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate<String, User> redisTemplate;

    @Autowired
    private AliOSSUtils aliOSSUtils;

    @Override
    public String home(String username) {
        return userMapper.getAvatarByUsername(username);
    }

    @Override
    public User personalhome(String username) {
        User user;
        boolean exists = Boolean.TRUE.equals(redisTemplate.hasKey(username));
        if (exists == true) {
            user = redisTemplate.opsForValue().get(username);
        } else {
            user = userMapper.getUserByUsername(username);
            redisTemplate.opsForValue().set(username, user, 43200000, TimeUnit.MILLISECONDS);
        }
        return user;
    }

    @Override
    public void update(String username, String newUsername, String newPassword, String newPhone, MultipartFile newAvatar) throws IOException {
        String newAvatarUrl = null;
        String oldAvatarUrl = this.personalhome(username).getAvatar();
        try {
            newAvatarUrl = aliOSSUtils.upload(newAvatar);
            userMapper.updateUser(username, newUsername, newPassword, newPhone, newAvatarUrl);
            redisTemplate.delete(username);
            if (!oldAvatarUrl.equals("https://lost-and-found-system-bucket.oss-cn-hangzhou.aliyuncs.com/avatar.jpg")) {
                aliOSSUtils.delete(oldAvatarUrl);
            }
        } catch (Exception e) {
            if (newAvatarUrl != null) {
                aliOSSUtils.delete(newAvatarUrl);
            }
            throw e;
        }
    }

    @Override
    public void logout(String username) {
        redisTemplate.delete(username);
    }

    @Override
    public void cancel(String username) {
        redisTemplate.delete(username);
        String avatar = userMapper.getAvatarByUsername(username);
        userMapper.deleteUserByUsername(username);
        if (!avatar.equals("https://lost-and-found-system-bucket.oss-cn-hangzhou.aliyuncs.com/avatar.jpg")) {
            aliOSSUtils.delete(avatar);
        }
    }
}
