package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LoginMapper {
    @Select("select count(*) from [user] where username = #{username} and password = #{password}")
    Integer getUserCountByUsernameAndPassword(String username, String password);
}
