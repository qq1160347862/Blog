package com.example.blog.controller;

import com.example.blog.Utils.Result;
import com.example.blog.entity.Sort;
import com.example.blog.entity.SortTools.IdList;
import com.example.blog.service.SortService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class SortController {

    @Autowired
    private SortService sortService;

    @GetMapping("/sort/getSortIdAndSortName")
    public Result sortIdAndSortName(){
        Result result = sortService.getSortIdAndSortName();
        return result;
    }

    @GetMapping("/sort")
    public Result SortByPage(@Param("current") int current,
                              @Param("size") int size){
        Result result = sortService.getSortByPage(current,size);
        return result;
    }

    @PostMapping("/sort")
    public Result addSort(@RequestBody Sort sort){
        Result result = sortService.addSort(sort);
        return result;
    }

    @PutMapping("/sort")
    public Result updateSort(@RequestBody Sort sort){
        Result result = sortService.updateSort(sort);
        return result;
    }

    @DeleteMapping("/sort")
    public Result deleteSort(@RequestBody IdList idList){
        Result result = sortService.deleteSort(idList);
        return result;
    }
}
