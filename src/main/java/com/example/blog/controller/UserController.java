package com.example.blog.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.blog.Utils.Result;
import com.example.blog.entity.User;
import com.example.blog.mapper.UserMapper;
import com.example.blog.service.UserService;
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
    @GetMapping("/user/{id}")
    public String getUserById(@PathVariable int id){
        System.out.println(id);
        return "返回用户Id:" + id;
    }
    @GetMapping("/user")
    public List getAllUsers(){


        List<User> list = userMapper.selectList(null);
        System.out.println(list);
        return list;
    }
    @GetMapping("user/findByPage")
    public IPage findByPage(){
        //设置页数和数据Size
        Page<User> page = new Page<>(0,1);
        IPage iPage = userMapper.selectPage(page,null);
        return iPage;
    }

    @PostMapping("/user")
    public int addUser(User user){

        int res = userMapper.insert(user);

        return res;
    }
    @PutMapping("/user")
    public String update(User user){
        return "更新用户";
    }
    @DeleteMapping("/user/{id}")
    public String deleteUserById(@PathVariable int id){
        System.out.println(id);
        return "被删除的用户Id:" + id;
    }
}
