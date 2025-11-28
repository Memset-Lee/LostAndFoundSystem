package org.example.service.impl;

import org.example.mapper.RegisterMapper;
import org.example.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    private RegisterMapper registerMapper;

    @Override
    public void register(String username, String password) {
        registerMapper.addUser(username, password);
    }
}
