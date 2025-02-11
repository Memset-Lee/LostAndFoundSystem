package org.example.controller;

import org.example.pojo.Item;
import org.example.pojo.Result;
import org.example.service.impl.ItemServiceImpl;
import org.example.utils.AliOSSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class ItemController {
    @Autowired
    private ItemServiceImpl itemServiceImpl;

    @Autowired
    private AliOSSUtils aliOSSUtils;

    @PostMapping("/api/post/{type}")
    public Result post(@PathVariable("type") String stringType,
                       @RequestPart("name") String name,
                       @RequestPart("phone") String phone,
                       @RequestPart("description") String description,
                       @RequestPart("location") String location,
                       @RequestPart("image") MultipartFile image) throws IOException {
        Integer integerType = Integer.valueOf(stringType);
        String imageUrl = aliOSSUtils.upload(image);
        itemServiceImpl.post(name, phone, description, location, imageUrl, integerType);
        return Result.success();
    }

    @GetMapping("/api/lostitems")
    public Result ListLostItems() {
        List<Item> ansList = itemServiceImpl.ListLostItems();
        return Result.success(ansList);
    }

    @GetMapping("/api/founditems")
    public Result ListFoundItems() {
        List<Item> ansList = itemServiceImpl.ListFoundItems();
        return Result.success(ansList);
    }
}
