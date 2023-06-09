package com.example.blog.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.blog.Utils.Result;
import com.example.blog.entity.Article;
import com.example.blog.entity.ArticleTools.IdList;
import com.example.blog.service.ArticleService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    //默认按时间排序
    @GetMapping("/article/findByPage")
    public Result getArticleByPage(@Param("current") int current,
                             @Param("size") int size){
        Result result = articleService.findByPage(current,size);
        return result;
    }

    //按历史记录排序(降序)
    @GetMapping("/article/findByPageByHistory")
    public Result getArticleByPageByHistory(@Param("current") int current,
                                            @Param("size") int size){
        Result result = articleService.findByPageByHistory(current,size);
        return result;
    }

    //按推荐指数排序
    @GetMapping("/article/findByPageByRecommend")
    public Result getArticleByPageByRecommend(@Param("current") int current,
                                            @Param("size") int size){
        Result result = articleService.findByPageByRecommend(current,size);
        return result;
    }




    //分页获取文章内容(无条件)
    @GetMapping("/article/ArticleByPage")
    public Result ArticleByPage(@Param("current") int current,
                                @Param("size") int size){
        Result result = articleService.getArticleByPage(current,size);
        return result;
    }
    //分页获取文章内容(模糊查询)
    @GetMapping("/article/likeArticleByPage")
    public Result likeArticleByPage(@Param("current") int current,
                                    @Param("size") int size,
                                    @Param("query") String query){
        Result result = articleService.likeArticleByPage(current,size,query);
        return  result;
    }

    //获取文章目录(ID,title)
    @GetMapping("/article/getArticleCatalogue")
    public Result getArticleCatalogue(){
        Result result = articleService.getArticleCatalogue();
        return result;
    }
    //根据ID查询文章
    @GetMapping("/article/getArticleById")
    public Result getArticleById(@Param("articleId") int articleId){
        Result result = articleService.getArticleById(articleId);
        return result;
    }
    //获取分类目录
    @GetMapping("/article/getArticleAndSort")
    public Result getArticleAndSort(@Param("sortId") int sortId){
        Result result = articleService.getArticleAndSort(sortId);
        return result;
    }



    @PostMapping("/article")
    public Result addArticle(@RequestBody Article article){
        Result result = articleService.addArticle(article);
        return result;
    }

    @PutMapping("/article")
    public Result updateArticle(@RequestBody Article article){
        Result result = articleService.updateArticle(article);
        return result;
    }

    @DeleteMapping("/article")
    public Result deleteArticle(@RequestBody IdList idList){
        Result result = articleService.deleteArticle(idList);
        return result;
    }

    //测试接口
    @GetMapping("/article/test")
    public Result test(@Param("sortId") int sortId){
        Result result = articleService.testService(sortId);
        return result;
    }
}
