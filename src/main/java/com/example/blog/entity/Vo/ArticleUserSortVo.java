package com.example.blog.entity.Vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class ArticleUserSortVo {

    @TableId(value = "article_id",type = IdType.AUTO)
    private Integer articleId;
    private String userName;
    private String title;
    private String content;
    private Integer historyCount;
    private String labelName;
    private Date pubDate;
    private Integer sortId;
    private String sortName;
    private Integer recommend;
}
