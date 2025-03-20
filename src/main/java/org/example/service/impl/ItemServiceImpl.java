package org.example.service.impl;

import org.example.mapper.ItemMapper;
import org.example.pojo.Item;
import org.example.service.ItemService;
import org.example.utils.AliOSSUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private AliOSSUtils aliOSSUtils;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void post(String name, String phone, String description, String location, MultipartFile image, Integer type) throws IOException {
        Map<String, Object> message = new HashMap<>();
        message.put("name", name);
        message.put("phone", phone);
        message.put("description", description);
        message.put("location", location);
        message.put("image", image.getBytes());
        message.put("type", type);
        rabbitTemplate.convertAndSend("post.direct", "post", message);
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
