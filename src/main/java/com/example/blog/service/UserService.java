package com.example.blog.service;

import com.example.blog.Utils.JwtUtils;
import com.example.blog.Utils.Result;
import com.example.blog.entity.User;
import com.example.blog.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService{
    private String message;

    @Resource
    UserMapper userMapper;

    public UserService(){

    }

    public Result loginInfer(User user){

        List<User> list = userMapper.getUserNameAndPassword(user.getUserName(), user.getPassword());
        System.out.println(list);
        if (!list.isEmpty()){
            message = "存在用户，即将生成Token返回";
            String token = JwtUtils.generateToken(user.getUserName());
            return Result.ok(message).data("token",token);
        }else {
            message = "用户不存在";
            return Result.error(message);
        }
//         用户返回token时,获取用户名，然后以此来判断是否是当前用户（是否要查询数据库？应该不用）
//        String username = JwtUtils.getClaimsByToken(token).getSubject();
    }

    public Result getUserIdAndUserName(){

        List<User> list = userMapper.getUserIdAndUserName();
        System.out.println(list);
        if (!list.isEmpty()){
            message = "返回userName和userId";
            return Result.ok(message).data("list",list);
        }else {
            message = "返回异常";
            return Result.error(message);
        }
    }
}
