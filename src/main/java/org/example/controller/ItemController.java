package org.example.controller;

import org.example.pojo.Item;
import org.example.pojo.Result;
import org.example.service.impl.ItemServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class ItemController {
    @Autowired
    private ItemServiceImpl itemServiceImpl;

    @PostMapping("/api/post/{type}")
    public Result post(@PathVariable("type") String type,
                       @RequestPart("name") String name,
                       @RequestPart("phone") String phone,
                       @RequestPart("description") String description,
                       @RequestPart("location") String location,
                       @RequestPart("image") MultipartFile image) throws IOException {
        itemServiceImpl.post(name, phone, description, location, image, type);
        return Result.success();
    }

    @GetMapping("/api/lostitems")
    public Result listLostItems() {
        List<Item> ansList = itemServiceImpl.listLostItems();
        return Result.success(ansList);
    }

    @GetMapping("/api/founditems")
    public Result listFoundItems() {
        List<Item> ansList = itemServiceImpl.listFoundItems();
        return Result.success(ansList);
    }

    @PostMapping("/api/delete")
    public Result deleteItem(@RequestBody Integer id) {
        itemServiceImpl.delete(id);
        return Result.success();
    }
}
