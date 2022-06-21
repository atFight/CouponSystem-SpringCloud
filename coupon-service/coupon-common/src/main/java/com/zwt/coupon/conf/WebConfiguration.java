package com.zwt.coupon.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;


/**
 * 定制HTTP消息转换器
 * java实体类与HTTP json 字符串相互转换
 * */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        WebMvcConfigurer.super.configureMessageConverters(converters);
        converters.clear();
        converters.add(new MappingJackson2HttpMessageConverter());
    }
}
