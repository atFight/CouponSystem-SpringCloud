package com.zwt.coupon.template.service;

import com.zwt.coupon.template.entity.CouponTemplate;

public interface IAsyncService {
    /**
     * <h2>根据模板异步的创建优惠券码</h2>
     * @param template {@link CouponTemplate} 优惠券模板实体
     * */
    void asyncConstructCouponByTemplate(CouponTemplate template);
}
