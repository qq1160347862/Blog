package com.example.blog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")      //允许跨域访问的路径
                .allowedOriginPatterns("*")  //报错就用allowedOriginPatterns             //循序跨域的源
                .allowedMethods("POST","GET","PUT","DELETE","OPTIONS")      //允许跨域请求的方法
                .allowedHeaders("*")                //允许头部设置
                .allowCredentials(true)             //允许cookies
                .maxAge(168000);         //预检测间隔时间(到时发送OPTION请求)
    }
}
