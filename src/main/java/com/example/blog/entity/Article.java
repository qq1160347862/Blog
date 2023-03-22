package com.example.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("articles")
public class Article {
    //设置主键自增
    @TableId(value = "article_id",type = IdType.AUTO)
    private Integer articleId;
    @TableField("title") //特殊注解
    private String title;
    @TableField("content")
    private String content;
    private Integer userId;
    private Integer historyCount;
    private Integer labelId;
    private Date pubDate;
    @TableField("recommend")
    private Integer recommend;
}
