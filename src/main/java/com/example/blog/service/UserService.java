package com.example.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.blog.Utils.JwtUtils;
import com.example.blog.Utils.Result;
import com.example.blog.entity.Article;
import com.example.blog.entity.User;
import com.example.blog.entity.UserTools.IdList;
import com.example.blog.mapper.UserMapper;
import org.apache.ibatis.annotations.Param;
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

    //用于拉下选择框
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

    //分页获取用户信息
    public Result getUserByPage(int current, int size){
        Page<User> page = new Page<>(current,size);
        IPage<User> iPage = userMapper.selectPage(page,null);

        if(iPage != null){
            message = "当前页:"+current+" 页大小:"+size;
            return Result.ok(message).data("userByPage",iPage);
        }else {
            message = "查询内容为空";
            return Result.error(message).data("userByPage", null);
        }
    }
    //分页获取用户信息(模糊查询)
    public Result likeUserByPage(int current, int size, String query){
        //临时创建User对象,用于查询
        User user = new User();
        user.setUserName(query);

        Page<User> page = new Page<>(current,size);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //sql拼接条件、数据库对应表项名、查询条件
        //like() => user_name like = '%query%'
        queryWrapper.like(user.getUserName() != null, "user_name",user.getUserName());
        IPage<User> iPage = userMapper.selectPage(page,queryWrapper);

        if(iPage != null){
            message = "当前页:"+current+" 页大小:"+size;
            return Result.ok(message).data("likeUserByPage",iPage);
        }else {
            message = "查询内容为空";
            return Result.error(message).data("likeUserByPage", null);
        }

    }


    public Result addUser(User user){
        userMapper.addUser(user.getUserName(),
                user.getPassword(),
                user.getUserDesc());
        return Result.ok();
    }

    public Result updateUser(User user){
        userMapper.updateUser(user.getUserName(),
                user.getPassword(),
                user.getUserDesc(),
                user.getUserId());
        return Result.ok();
    }

    public Result deleteUser(IdList list){
        //批量删除
        userMapper.deleteBatchIds(list.getIdList());
        return Result.ok();
    }
}
