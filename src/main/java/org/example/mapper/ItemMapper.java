package org.example.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.pojo.Item;

import java.util.List;

@Mapper
public interface ItemMapper {
    @Insert("insert into item values (#{name}, #{phone}, #{description}, #{location}, #{imageUrl}, #{type})")
    void insertItem(String name, String phone, String description, String location, String imageUrl, Integer type);

    @Select("select name, phone, description, location, image from item where type = 0")
    List<Item> selectLostItems();

    @Select("select name, phone, description, location, image from item where type = 1")
    List<Item> selectFoundItems();
}
