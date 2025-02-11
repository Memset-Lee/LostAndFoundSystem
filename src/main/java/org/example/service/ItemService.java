package org.example.service;

import org.example.pojo.Item;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
public interface ItemService {
    public void post(String name, String phone, String description, String location, String imageUrl, Integer type);

    public List<Item> ListLostItems();

    public List<Item> ListFoundItems();
}
