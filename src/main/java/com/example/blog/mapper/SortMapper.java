package com.example.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.blog.entity.Label;
import com.example.blog.entity.Sort;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SortMapper extends BaseMapper<Sort> {

    @Select("SELECT DISTINCT * FROM sorts")
    List<Sort> getSortIdAndSortName();
}
