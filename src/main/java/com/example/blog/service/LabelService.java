package com.example.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.blog.Utils.Result;
import com.example.blog.entity.Label;
import com.example.blog.entity.LabelTools.IdList;
import com.example.blog.mapper.LabelMapper;
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

    //分页获取标签信息
    public Result getLabelByPage(int current, int size){
        Page<Label> page = new Page<>(current,size);
        IPage<Label> iPage = labelMapper.selectPage(page,null);

        if(iPage != null){
            message = "当前页:"+current+" 页大小:"+size;
            return Result.ok(message).data("labelByPage",iPage);
        }else {
            message = "查询内容为空";
            return Result.error(message).data("labelByPage", null);
        }
    }
    //分页获取标签信息(模糊查询)
    public Result likeLabelByPage(int current, int size, String query){
        //临时创建Sort对象,用于查询
        Label label = new Label();
        label.setLabelName(query);

        Page<Label> page = new Page<>(current,size);
        QueryWrapper<Label> queryWrapper = new QueryWrapper<>();
        //sql拼接条件、数据库对应表项名、查询条件
        //like() => label_name like = '%query%'
        queryWrapper.like(label.getLabelName() != null, "label_name",label.getLabelName());
        IPage<Label> iPage = labelMapper.selectPage(page,queryWrapper);

        if(iPage != null){
            message = "当前页:"+current+" 页大小:"+size;
            return Result.ok(message).data("likeLabelByPage",iPage);
        }else {
            message = "查询内容为空";
            return Result.error(message).data("likeLabelByPage", null);
        }
    }


    public Result addLabel(Label label){
//        labelMapper.addLabel(label.getLabelName());
        labelMapper.insert(label);
        return Result.ok();
    }

    public Result updateLabel(Label label){
        labelMapper.updateById(label);
        return Result.ok();
    }

    public Result deleteLabel(IdList idList){
        //批量删除
        labelMapper.deleteBatchIds(idList.getIdList());
        return Result.ok();
    }
}
