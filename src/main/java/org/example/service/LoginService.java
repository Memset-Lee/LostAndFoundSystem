package org.example.service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
public interface LoginService {
    public Integer login(String username, String password);
}
