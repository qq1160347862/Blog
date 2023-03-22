package com.example.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("labels")
public class Label {
    private String labelName;
    //设置主键自增
    @TableId(value = "label_id",type = IdType.AUTO)
    private Integer labelId;
}
