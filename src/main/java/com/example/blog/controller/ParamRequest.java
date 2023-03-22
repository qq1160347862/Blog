package com.example.blog.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//只请求数据用RestController，页面和数据用Controller
//RestController注解会将返回的 对象数据转化成JSON对象
//GetMapping获取资源;PostMapping新增资源;PutMapping更新资源
//DeleteMapping删除资源;PatchMapping更新部分资源
@RestController
public class ParamRequest {

    //接受get请求的参数/hello?name=zhagnsan&phone=123
    //RequestParam表示必须带参
    @GetMapping("/hello")
    public String hello(String name,String phone){

        return "你好" + name + phone;
    }

    //@PostMapping传输body，可带参，一般传输实体对象数据
    //属性名称和类型前后端一致！！
    //传输数据类型一般是JSON并使用@RequestBody
}
