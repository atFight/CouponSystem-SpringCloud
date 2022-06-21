package com.zwt.coupon.advice;

import com.zwt.coupon.annotation.IgnoreResponseAdvice;
import com.zwt.coupon.vo.CommonResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;


/*
 *  拦截controller的返回
 *  */

@RestControllerAdvice
@SuppressWarnings("all")
public class CommonResponseDataAdvice implements ResponseBodyAdvice<Object> {

    /*
     *  判断是否需要对响应进行处理
     *  */
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {

        //判断类或者方法是否带有IgnoreResponseAdvice注解
        if (methodParameter.getDeclaringClass().isAnnotationPresent(IgnoreResponseAdvice.class)
                || methodParameter.getMethod().isAnnotationPresent(IgnoreResponseAdvice.class)) {
            return false;
        }
        return true;
    }

    /*
     *  当supports方法返回true时，执行该方法
     *  在返回Http Response前对Object进行处理
     *  */
    @Override
    public Object beforeBodyWrite(Object o,
                                  MethodParameter methodParameter,
                                  MediaType mediaType,
                                  Class<? extends HttpMessageConverter<?>> aClass,
                                  ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {

        CommonResponse<Object> response = new CommonResponse<>(0, "");
        if (o == null) {
            return response;
        }else if (o instanceof CommonResponse) {
            response = (CommonResponse<Object>) o;
        }else {
            response.setData(o);
        }
        return response;
    }
}
