package com.example.blog.controller;

import com.example.blog.Utils.Result;
import com.example.blog.service.SortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SortController {

    @Autowired
    private SortService sortService;

    @GetMapping("/sort/getSortIdAndSortName")
    public Result sortIdAndSortName(){
        Result result = sortService.getSortIdAndSortName();
        return result;
    }
}
