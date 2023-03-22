package com.example.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("users")
public class User {
    //数据的驼峰命名对应数据库下划线分割
    //设置主键自增
    @TableId(value = "user_id",type = IdType.AUTO)
    private Integer userId ;
    private String userName;
    @TableField("password") //特殊注解
    private String password;
    private String userDesc;
}
