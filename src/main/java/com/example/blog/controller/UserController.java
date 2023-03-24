package com.example.blog.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.blog.Utils.Result;
import com.example.blog.entity.User;
import com.example.blog.entity.UserTools.IdList;
import com.example.blog.mapper.UserMapper;
import com.example.blog.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;

    @PostMapping("/user/login")
    public Result login(@RequestBody User user){
        System.out.println(user);

        Result result = userService.loginInfer(user);

        return result;
    }

    @GetMapping("/user/getUserIdAndUserName")
    public Result userIdAndUserName(){
        Result result = userService.getUserIdAndUserName();
        return result;
    }

    @GetMapping("/user")
    public Result UserByPage(@Param("current") int current,
                             @Param("size") int size){
        Result result = userService.getUserByPage(current,size);
        return result;
    }
    @GetMapping("/user/likeUserByPage")
    public Result likeUserByPage(@Param("current") int current,
                                 @Param("size") int size,
                                 @Param("query") String query){
        Result result = userService.likeUserByPage(current,size,query);
        return result;
    }


    @PostMapping("/user")
    public Result addUser(@RequestBody User user){
        Result result = userService.addUser(user);
        return result;
    }
    @PutMapping("/user")
    public Result updateUser(@RequestBody User user){
        Result result = userService.updateUser(user);
        return result;
    }
    @DeleteMapping("/user")
    public Result deleteUser(@RequestBody IdList idList){
        Result result = userService.deleteUser(idList);
        return result;
    }

    @GetMapping("/user/{id}")
    public String getUserById(@PathVariable int id){
        System.out.println(id);
        return "返回用户Id:" + id;
    }

    @GetMapping("user/findByPage")
    public IPage findByPage(){
        //设置页数和数据Size
        Page<User> page = new Page<>(0,1);
        IPage iPage = userMapper.selectPage(page,null);
        return iPage;
    }

}
