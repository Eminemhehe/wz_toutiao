package com.example.doutiao.configuration;


import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.example.doutiao.interceptor.LoginRequiredInterceptor;
import com.example.doutiao.interceptor.PassportInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wz on 2019/5/15.
 */
//添加拦截器
@Configuration
public class ToutiaoWevConfiguration extends WebMvcConfigurerAdapter {

    @Resource
    PassportInterceptor passportInterceptor;

    @Resource
    LoginRequiredInterceptor loginRequiredInterceptor;

    //自动配置，注册，每次启动代码就会启动整个interception
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(passportInterceptor);
        registry.addInterceptor(loginRequiredInterceptor).addPathPatterns("/msg/*").addPathPatterns("/like").addPathPatterns("/dislike");

        super.addInterceptors(registry);
    }

}
