package com.zwt.coupon.template.service;

import com.zwt.coupon.exception.CouponException;
import com.zwt.coupon.template.entity.CouponTemplate;
import com.zwt.coupon.template.vo.TemplateRequest;

public interface IBuildTemplateService {
    /**
     * <h2>创建优惠券模板</h2>
     * @param request {@link TemplateRequest} 模板信息请求对象
     * @return {@link CouponTemplate} 优惠券模板实体
     * */
    CouponTemplate buildTemplate(TemplateRequest request)
            throws CouponException;
}
