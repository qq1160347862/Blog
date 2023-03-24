package com.example.blog.controller;

import com.example.blog.Utils.Result;
import com.example.blog.entity.Label;
import com.example.blog.entity.LabelTools.IdList;
import com.example.blog.service.LabelService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class LabelController {

    @Autowired
    private LabelService labelService;

    @GetMapping("/label/getLabelIdAndLabelName")
    public Result labelIdAndLabelName(){
        Result result = labelService.getLabelIdAndLabelName();
        return result;
    }

    @GetMapping("/label")
    public Result LabelByPage(@Param("current") int current,
                              @Param("size") int size){
        Result result = labelService.getLabelByPage(current,size);
        return result;
    }
    @GetMapping("/label/likeLabelByPage")
    public Result likeSortByPage(@Param("current") int current,
                                 @Param("size") int size,
                                 @Param("query") String query){
        Result result = labelService.likeLabelByPage(current,size,query);
        return result;
    }

    @PostMapping("/label")
    public Result addLabel(@RequestBody Label label){
        Result result = labelService.addLabel(label);
        return result;
    }

    @PutMapping("/label")
    public Result updateLabel(@RequestBody Label label){
        Result result = labelService.updateLabel(label);
        return result;
    }

    @DeleteMapping("/label")
    public Result deleteLabel(@RequestBody IdList idList){
        Result result = labelService.deleteLabel(idList);
        return result;
    }

}
