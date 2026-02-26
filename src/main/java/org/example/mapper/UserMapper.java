package org.example.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.pojo.User;

@Mapper
public interface UserMapper {
    @Select("select avatar from [user] where username = #{username}")
    String getAvatarByUsername(String username);

    @Select("select * from [user] where username = #{username}")
    User getUserByUsername(String username);

    @Update("UPDATE [user] SET username = #{newUsername}, password = #{newPassword}, phone = #{newPhone}, avatar = #{newAvatarUrl} WHERE username = #{username}")
    void updateUser(String username, String newUsername, String newPassword, String newPhone, String newAvatarUrl);

    @Delete("delete from [User] where username = #{username}")
    void deleteUserByUsername(String username);
}
