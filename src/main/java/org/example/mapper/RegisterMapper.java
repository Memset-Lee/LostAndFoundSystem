package org.example.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RegisterMapper {
    @Insert("insert into [user] values (#{username}, #{password}, null ,'https://lost-and-found-system-bucket.oss-cn-hangzhou.aliyuncs.com/avatar.jpg')")
    void addUser(String username, String password);
}
