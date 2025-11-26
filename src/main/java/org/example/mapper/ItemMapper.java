package org.example.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.pojo.Item;

import java.util.List;

@Mapper
public interface ItemMapper {
    @Insert("insert into item values (#{name}, #{phone}, #{description}, #{location}, #{imageUrl}, #{type})")
    void insertItem(String name, String phone, String description, String location, String imageUrl, String type);

    @Select("select id, name, phone, description, location, image, type from item where type = 'lost'")
    List<Item> selectLostItems();

    @Select("select id, name, phone, description, location, image, type from item where type = 'found'")
    List<Item> selectFoundItems();

    @Select("select image from item where id = #{id}")
    String selectImageById(Integer id);

    @Delete("delete from item where id = #{id}")
    void deleteItem(Integer id);
}
