package com.example.blog.service;

import com.example.blog.Utils.Result;
import com.example.blog.entity.Label;
import com.example.blog.mapper.LabelMapper;
import com.example.blog.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LabelService {

    private String message;

    @Resource
    LabelMapper labelMapper;

    public LabelService(){

    }

    public Result getLabelIdAndLabelName(){
        List<Label> list = labelMapper.getLabelIdAndLabelName();
        System.out.println(list);
        if (!list.isEmpty()){
            message = "返回labelName和labelId";
            return Result.ok(message).data("list",list);
        }else {
            message = "返回异常";
            return Result.error(message);
        }
    }
}
