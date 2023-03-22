package com.example.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.blog.entity.Article;
import com.example.blog.entity.Vo.ArticleUserSortVo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface ArticleMapper extends BaseMapper<Article> {
    Page<ArticleUserSortVo> findArticlesAndUserNameAndSortName(Page<ArticleUserSortVo> page);

    Page<ArticleUserSortVo> findArticlesAndUserNameAndSortNameByHistory(Page<ArticleUserSortVo> page);

    Page<ArticleUserSortVo> findArticleAndUserNameAndSortNameByRecommend(Page<ArticleUserSortVo> page);

    void addArticle(@Param("title") String title,
                    @Param("content") String content,
                    @Param("userId") Integer userId,
                    @Param("sortId") Integer sortId,
                    @Param("historyCount") Integer historyCount,
                    @Param("labelId") Integer labelId,
                    @Param("pubDate") Date pubDate,
                    @Param("recommend") Integer recommend);
}
