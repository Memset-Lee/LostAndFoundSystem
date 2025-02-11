package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.pojo.User;

import java.util.List;

@Mapper
public interface UserMapper {
    List<User> selectUser(String username);

    @Select("select avatar from [user] where username = #{username}")
    String getAvatar(String username);

    @Select("select * from [user] where username = #{username}")
    User getUser(String username);

    @Update("UPDATE [user] SET username = #{newUsername}, password = #{newPassword}, phone = #{newPhone}, avatar = #{newAvatarUrl} WHERE username = #{username}")
    void updateUser(String username, String newUsername, String newPassword, String newPhone, String newAvatarUrl);
}
