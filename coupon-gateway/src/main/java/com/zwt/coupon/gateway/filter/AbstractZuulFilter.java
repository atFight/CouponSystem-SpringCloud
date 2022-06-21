package com.zwt.coupon.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

public abstract class AbstractZuulFilter extends ZuulFilter {

    private final static String NEXT = "next";

    //过滤器之间相互通信
    protected RequestContext context;
    protected final static String TIMESTAMP = "startTime";

    @Override
    public boolean shouldFilter() {
        return (boolean)RequestContext.getCurrentContext().getOrDefault(NEXT, true);
    }

    @Override
    public Object run() throws ZuulException {
        context = RequestContext.getCurrentContext();
        return cRun();
    }

    protected abstract Object cRun();

    Object fail(int code, String msg) {
        context.set(NEXT, false);
        context.setSendZuulResponse(false);
        context.getResponse().setContentType("text/html;charset=UTF-8");
        context.setResponseStatusCode(code);
        context.setResponseBody(String.format("{\"result\": \"%s!\"}", msg));
        return null;
    }

    Object success() {
        context.set(NEXT, true);
        return null;
    }

}
