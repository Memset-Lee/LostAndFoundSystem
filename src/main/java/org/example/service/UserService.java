package org.example.service;

import org.example.pojo.User;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
public interface UserService {
    public List<User> listUser();

    public String home(String username);

    public User personalhome(String username);

    public void update(String username, String newUsername, String newPassword, String newPhone, String newAvatarUrl);
}
