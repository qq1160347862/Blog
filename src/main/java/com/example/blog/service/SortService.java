package com.example.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.blog.Utils.Result;
import com.example.blog.entity.Sort;
import com.example.blog.entity.SortTools.IdList;
import com.example.blog.entity.User;
import com.example.blog.mapper.SortMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SortService {

    private String message;

    @Resource
    SortMapper sortMapper;

    public SortService(){

    }

    public Result getSortIdAndSortName(){
        List<Sort> list = sortMapper.getSortIdAndSortName();
        System.out.println(list);
        if (!list.isEmpty()){
            message = "返回sortName和sortId";
            return Result.ok(message).data("list",list);
        }else {
            message = "返回异常";
            return Result.error(message);
        }
    }

    //分页分类标签信息
    public Result getSortByPage(int current, int size){
        Page<Sort> page = new Page<>(current,size);
        IPage<Sort> iPage = sortMapper.selectPage(page,null);

        if(iPage != null){
            message = "当前页:"+current+" 页大小:"+size;
            return Result.ok(message).data("sortByPage",iPage);
        }else {
            message = "查询内容为空";
            return Result.error(message).data("sortByPage", null);
        }
    }
    //分页获取分类信息(模糊查询)
    public Result likeSortByPage(int current, int size, String query){
        //临时创建Sort对象,用于查询
        Sort sort = new Sort();
        sort.setSortName(query);

        Page<Sort> page = new Page<>(current,size);
        QueryWrapper<Sort> queryWrapper = new QueryWrapper<>();
        //sql拼接条件、数据库对应表项名、查询条件
        //like() => sort_name like = '%query%'
        queryWrapper.like(sort.getSortName() != null, "sort_name",sort.getSortName());
        IPage<Sort> iPage = sortMapper.selectPage(page,queryWrapper);

        if(iPage != null){
            message = "当前页:"+current+" 页大小:"+size;
            return Result.ok(message).data("likeSortByPage",iPage);
        }else {
            message = "查询内容为空";
            return Result.error(message).data("likeSortByPage", null);
        }
    }



    public Result addSort(Sort sort){
        sortMapper.insert(sort);
        return Result.ok();
    }

    public Result updateSort(Sort sort){
        sortMapper.updateById(sort);
        return Result.ok();
    }

    public Result deleteSort(IdList idList){
        //批量删除
        sortMapper.deleteBatchIds(idList.getIdList());
        return Result.ok();
    }
}
