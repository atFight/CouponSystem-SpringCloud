package com.zwt.coupon.gateway.filter;

import org.springframework.stereotype.Component;

@Component
public class PreRequestFilter extends AbstractPreZuulFilter{
    @Override
    protected Object cRun() {
        /* 记录客户端发起请求的时间戳 */
        context.set(TIMESTAMP, System.currentTimeMillis());
        return success();
    }

    @Override
    public int filterOrder() {
        return 0;
    }
}
