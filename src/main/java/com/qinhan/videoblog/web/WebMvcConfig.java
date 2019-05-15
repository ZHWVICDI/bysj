package com.qinhan.videoblog.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

//为了在保证spring boot有效的同时增加自己的配置 ,这些配置属于spring boot自动配置的盲区
/*@Configuration*/
public class WebMvcConfig extends WebMvcConfigurationSupport {
    private static long FILE_SIZE=600*1024*1024;
    @Bean
    public MultipartResolver multipartResolver(){
        CommonsMultipartResolver resolver=new CommonsMultipartResolver();
        //reso
        resolver.setResolveLazily(true);
        resolver.setMaxUploadSize(FILE_SIZE);
        return resolver;
    }
    
}
