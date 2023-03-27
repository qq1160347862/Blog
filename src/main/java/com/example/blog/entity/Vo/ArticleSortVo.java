package com.example.blog.entity.Vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class ArticleSortVo {

    @TableId(value = "article_id",type = IdType.AUTO)
    private Integer articleId;
    private String title;
    private String sortName;
    private Integer sortId;
}
