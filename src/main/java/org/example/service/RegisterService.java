package org.example.service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
public interface RegisterService {
    public void register(String username, String password);
}
