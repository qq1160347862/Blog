package com.example.blog.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.blog.Utils.Result;
import com.example.blog.service.ArticleService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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

    //测试接口
    @GetMapping("/article/test")
    public Result test(@Param("current") int current,
                                   @Param("size") int size){
        Result result = articleService.selectUserNameAndArticleId(current,size);
        return result;
    }
}
