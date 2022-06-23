package com.zwt.coupon.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
public class AccessLogFilter extends AbstractPostZuulFilter{
    private static final String TAG = "[aTang][AccessLogFilter]";

    @Override
    protected Object cRun() {
        HttpServletRequest request = context.getRequest();
        Long startTime = (Long) context.get(TIMESTAMP);
        String uri = request.getRequestURI();
        long duration = System.currentTimeMillis() - startTime;

        log.info(TAG + "uri: {}, duration: {}", uri, duration);
        return success();
    }

    @Override
    public int filterOrder() {
        return FilterConstants.SEND_RESPONSE_FILTER_ORDER - 1;
    }
}
