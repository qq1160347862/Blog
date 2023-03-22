package com.example.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("sorts")
public class Sort {
    //设置主键自增
    @TableId(value = "sort_id",type = IdType.AUTO)
    private Integer sortId;
    private String sortName;
}
