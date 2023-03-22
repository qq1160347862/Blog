package com.example.blog.controller;

import com.example.blog.Utils.Result;
import com.example.blog.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LabelController {

    @Autowired
    private LabelService labelService;

    @GetMapping("/label/getLabelIdAndLabelName")
    public Result labelIdAndLabelName(){
        Result result = labelService.getLabelIdAndLabelName();
        return result;
    }
}
