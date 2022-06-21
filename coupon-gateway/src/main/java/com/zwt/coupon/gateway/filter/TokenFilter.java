package com.zwt.coupon.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
public class TokenFilter extends AbstractPreZuulFilter{
    private static final String ERROR_MSG = "error: token is empty";
    private static final String TAG = "[TokenFilter]";

    @Override
    protected Object cRun() {
        HttpServletRequest request = context.getRequest();
        log.info(TAG + "request : " + request.getMethod());
        log.info(TAG + "request : " + request.getRequestURL().toString());

        Object token = request.getParameter("token");
        if (token == null) {
            log.error(ERROR_MSG);
            return fail(401, ERROR_MSG);
        }

        return success();
    }

    @Override
    public int filterOrder() {
        return 1;
    }
}
