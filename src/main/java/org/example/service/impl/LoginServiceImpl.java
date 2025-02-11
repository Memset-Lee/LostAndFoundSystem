package org.example.service.impl;

import org.example.mapper.LoginMapper;
import org.example.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private LoginMapper loginMapper;

    @Override
    public Integer login(String username, String password) {
        return loginMapper.count(username, password);
    }
}
