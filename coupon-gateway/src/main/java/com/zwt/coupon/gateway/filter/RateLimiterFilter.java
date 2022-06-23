package com.zwt.coupon.gateway.filter;


import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;


/* 限流过滤器 */
@Slf4j
@Component
@SuppressWarnings("all")
public class RateLimiterFilter extends AbstractPreZuulFilter{
    private static final String TAG = "[aTang][RateLimiterFilter]";

    /* 每秒可以获取2个令牌 */
    RateLimiter limiter = RateLimiter.create(2.0);

    @Override
    protected Object cRun() {
        HttpServletRequest request = context.getRequest();
        if (limiter.tryAcquire()) {
            log.info(TAG + "get rate token success");
            return success();
        }else {
            log.error(TAG + "rate limit: {}", request.getRequestURI());
            return fail(402, "error: rate limit");
        }
    }

    @Override
    public int filterOrder() {
        return 2;
    }
}
