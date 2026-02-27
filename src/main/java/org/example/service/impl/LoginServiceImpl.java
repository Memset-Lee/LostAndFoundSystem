package org.example.service.impl;

import org.example.mapper.LoginMapper;
import org.example.pojo.User;
import org.example.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private LoginMapper loginMapper;

    @Autowired
    private RedisTemplate<String, User> redisTemplate;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Override
    public boolean login(String username, String password) {
        Integer num = loginMapper.getUserCountByUsernameAndPassword(username, password);
        if (num != 0) {
            boolean exists = Boolean.TRUE.equals(redisTemplate.hasKey(username));
            if (exists == false) {
                redisTemplate.opsForValue().set(username, userServiceImpl.personalhome(username), 43200000, TimeUnit.MILLISECONDS);
            }
            return true;
        } else {
            return false;
        }
    }
}
