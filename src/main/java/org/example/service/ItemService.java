package org.example.service;

import org.example.pojo.Item;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
public interface ItemService {
    public void post(String name, String phone, String description, String location, MultipartFile image, Integer type) throws IOException;

    public List<Item> ListLostItems();

    public List<Item> ListFoundItems();
}
