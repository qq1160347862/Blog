package com.example.blog.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.blog.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

//    //查询所有用户
//    @Select("select * from users")
//    List<User> getAllUsers();
//
//    @Select("select * from users where user_name = #{userName} and password = #{password}")
//    List<User> getUserNameAndPassword(User user);

    //登录使用
    List<User> getUserNameAndPassword(@Param("userName") String userName,
                                             @Param("password") String password);
    //下拉框信息
    List<User> getUserIdAndUserName();

    void addUser(@Param("userName") String userName,
                 @Param("password") String password,
                 @Param("userDesc") String userDesc);

    void updateUser(@Param("userName") String userName,
                    @Param("password") String password,
                    @Param("userDesc") String userDesc,
                    @Param("userId") Integer userId);
}
