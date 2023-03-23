package com.example.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.blog.entity.Label;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LabelMapper extends BaseMapper<Label> {
    //查询所有用户
    @Select("SELECT DISTINCT * FROM labels")
    List<Label> getLabelIdAndLabelName();


}
