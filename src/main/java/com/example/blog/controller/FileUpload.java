package com.example.blog.controller;

import com.example.blog.Utils.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@RestController
public class FileUpload {

    @PostMapping("/upload")
    public Result upload(MultipartFile photo, HttpServletRequest request) throws IOException {



        //获取文件名
        System.out.println(photo.getOriginalFilename());
        //获取文件类型
        System.out.println(photo.getContentType());


        //动态获取文件存放位置
        String path = request.getServletContext().getRealPath("/upload/");
        System.out.println(path);

        //保存文件
        saveFile(photo,path);

        return Result.ok().data("imageName",photo.getOriginalFilename());
    }

    public void saveFile(MultipartFile photo, String path) throws IOException{

        //如果不存在上传目录则创建
        File dir = new File(path);
        if(!dir.exists()){

            dir.mkdir();
        }

        //新建文件对象
        File file = new File(path+photo.getOriginalFilename());
        photo.transferTo(file); //保存至本地
    }
}
