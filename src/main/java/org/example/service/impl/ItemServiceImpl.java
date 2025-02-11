package org.example.service.impl;

import org.example.mapper.ItemMapper;
import org.example.pojo.Item;
import org.example.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemMapper itemMapper;

    @Override
    public void post(String name, String phone, String description, String location, String imageUrl, Integer type) {
        itemMapper.insertItem(name, phone, description, location, imageUrl, type);
    }

    @Override
    public List<Item> ListLostItems() {
        return itemMapper.selectLostItems();
    }

    @Override
    public List<Item> ListFoundItems() {
        return itemMapper.selectFoundItems();
    }
}
