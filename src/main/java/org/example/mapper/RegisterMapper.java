package org.example.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RegisterMapper {
    @Insert("insert into [user] values (#{username}, #{password}, null ,'https://tse2-mm.cn.bing.net/th/id/OIP-C.EKJYdJ-gbrKGFyhfiHd1qwHaHa?rs=1&pid=ImgDetMain')")
    void insertUser(String username, String password);
}
