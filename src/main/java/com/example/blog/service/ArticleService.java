package com.example.blog.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.blog.Utils.Result;
import com.example.blog.entity.Article;
import com.example.blog.entity.Vo.ArticleUserSortVo;
import com.example.blog.mapper.ArticleMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service
public class ArticleService {
    private String message;
    @Resource
    private ArticleMapper articleMapper;

    public ArticleService(){

    }

    //默认加载 按时间排序
    public Result findByPage(int current, int size){

        //设置页数和数据Size
        Page<ArticleUserSortVo> page = new Page<>(current,size);
        IPage<ArticleUserSortVo> iPage = articleMapper.findArticlesAndUserNameAndSortName(page);

        if(iPage != null){
            message = "当前页:"+current+" 页大小:"+size;
            return Result.ok(message).data("articleByPage",iPage);
        }else {
            message = "查询内容为空";
            return Result.error(message).data("articleByPage", null);
        }
    }

    //按历史记录排序
    public Result findByPageByHistory(int current, int size){
        //设置页数和数据Size
        Page<ArticleUserSortVo> page = new Page<>(current,size);
        IPage<ArticleUserSortVo> iPage = articleMapper.findArticlesAndUserNameAndSortNameByHistory(page);

        if(iPage != null){
            message = "当前页:"+current+" 页大小:"+size;
            return Result.ok(message).data("articleByPage",iPage);
        }else {
            message = "查询内容为空";
            return Result.error(message).data("articleByPage", null);
        }
    }

    //按推荐指数排序
    public Result findByPageByRecommend(int current, int size){
        //设置页数和数据Size
        Page<ArticleUserSortVo> page = new Page<>(current,size);
        IPage<ArticleUserSortVo> iPage = articleMapper.findArticleAndUserNameAndSortNameByRecommend(page);

        if(iPage != null){
            message = "当前页:"+current+" 页大小:"+size;
            return Result.ok(message).data("articleByPage",iPage);
        }else {
            message = "查询内容为空";
            return Result.error(message).data("articleByPage", null);
        }
    }

    //分页获取文章内容(无条件查询)
    public Result getArticleByPage(int current, int size){
        Page<Article> page = new Page<>(current,size);
        IPage<Article> iPage = articleMapper.selectPage(page,null);

        if(iPage != null){
            message = "当前页:"+current+" 页大小:"+size;
            return Result.ok(message).data("articleByPage",iPage);
        }else {
            message = "查询内容为空";
            return Result.error(message).data("articleByPage", null);
        }
    }

    public Result addArticle(Article article){
        articleMapper.addArticle(article.getTitle(),
                                article.getContent(),
                                article.getUserId(),
                                article.getSortId(),
                                article.getHistoryCount(),
                                article.getLabelId(),
                                article.getPubDate(),
                                article.getRecommend());
        return Result.ok();
    }

    //测试接口
    public Result testService(int current, int size){
        //设置页数和数据Size

        Page<Article> page = new Page<>(current,size);
        IPage<Article> iPage = articleMapper.selectPage(page,null);
        System.out.println(iPage.getRecords());
        return Result.ok().data("page",iPage);
    }
}
