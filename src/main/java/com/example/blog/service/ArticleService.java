package com.example.blog.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.blog.Utils.Result;
import com.example.blog.entity.Article;
import com.example.blog.entity.ArticleTools.IdList;
import com.example.blog.entity.Vo.ArticleUserSortVo;
import com.example.blog.mapper.ArticleMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
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

    //分页获取文章内容(模糊查询)
    public Result likeArticleByPage(int current, int size, String query){
        //临时创建Article对象，用于查询
        Article article = new Article();
        article.setTitle(query);

        Page<Article> page = new Page<>(current,size);
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        //sql拼接条件、数据库对应表项名、查询条件
        //like() => title like = '%query%'
        queryWrapper.like(article.getTitle() != null,"title",article.getTitle());
        IPage<Article> iPage = articleMapper.selectPage(page,queryWrapper);

        if(iPage != null){
            message = "当前页:"+current+" 页大小:"+size;
            return Result.ok(message).data("likeArticleByPage",iPage);
        }else {
            message = "查询内容为空";
            return Result.error(message).data("likeArticleByPage", null);
        }
    }

    //获取文章所有ID
    public Result getArticleCatalogue(){
        List<Article> list = articleMapper.getAllArticleId();
        if (!list.isEmpty()){
            message = "查询目录";
            return Result.ok(message).data("articleCatalogue",list);
        }else {
            message= "目录未查询到";
            return Result.ok(message).data("articleCatalogue",null);
        }

    }
    //根据ID查询文章
    public Result getArticleById(int id){
        ArticleUserSortVo article = articleMapper.getArticleById(id);
        if (article.getArticleId() !=null){
            message = "查询文章";
            return Result.ok(message).data("article",article);
        }else {
            message = "文章未查询到";
            return Result.ok(message).data("article",null);
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

    public Result updateArticle(Article article){
        articleMapper.updateArticle(article.getTitle(),
                article.getContent(),
                article.getUserId(),
                article.getSortId(),
                article.getHistoryCount(),
                article.getLabelId(),
                article.getPubDate(),
                article.getRecommend(),
                article.getArticleId());
        return Result.ok();
    }

    public Result deleteArticle(IdList idList){
        //批量删除
        articleMapper.deleteBatchIds(idList.getIdList());
        return Result.ok();
    }

    //测试接口
    public Result testService(){
        //设置页数和数据Size

//        Page<Article> page = new Page<>(current,size);
//        IPage<Article> iPage = articleMapper.selectPage(page,null);
//        List<Integer> list = new ArrayList<>();
//        list.add(1);
//        articleMapper.deleteBatchIds(list);
//        System.out.println(iPage.getRecords());
        List<Article> list = articleMapper.getAllArticleId();

        return Result.ok().data("list",list);
    }
}
