package org.example.service.impl;

import org.example.mapper.ItemMapper;
import org.example.pojo.Item;
import org.example.service.ItemService;
import org.example.utils.AliOSSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private AliOSSUtils aliOSSUtils;

    @Override
    public void post(String name, String phone, String description, String location, MultipartFile image, Integer type) throws IOException {
        String imageUrl = null;
        try {
            imageUrl = aliOSSUtils.upload(image);
            itemMapper.insertItem(name, phone, description, location, imageUrl, type);
        } catch (Exception e) {
            if (imageUrl != null) {
                aliOSSUtils.delete(imageUrl);
            }
            throw e;
        }
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
