package org.example.service;

import org.example.pojo.User;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
public interface UserService {
    public List<User> listUser();

    public String home(String username);

    public User personalhome(String username);

    public void update(String username, String newUsername, String newPassword, String newPhone, MultipartFile newAvatar) throws IOException;

    public void logout(String username);

    public void cancel(String username);
}
