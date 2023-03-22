package com.example.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.blog.entity.Article;
import com.example.blog.entity.Vo.ArticleUserSortVo;

public interface ArticleMapper extends BaseMapper<Article> {
    Page<ArticleUserSortVo> findArticlesAndUserNameAndSortName(Page<ArticleUserSortVo> page);

    Page<ArticleUserSortVo> findArticlesAndUserNameAndSortNameByHistory(Page<ArticleUserSortVo> page);

    Page<ArticleUserSortVo> findArticleAndUserNameAndSortNameByRecommend(Page<ArticleUserSortVo> page);
}
