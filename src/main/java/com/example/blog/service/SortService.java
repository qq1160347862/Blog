package com.example.blog.service;

import com.example.blog.Utils.Result;
import com.example.blog.entity.Label;
import com.example.blog.entity.Sort;
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
}
