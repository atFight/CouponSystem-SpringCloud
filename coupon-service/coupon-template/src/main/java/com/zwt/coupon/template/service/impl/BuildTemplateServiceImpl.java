package com.zwt.coupon.template.service.impl;

import com.zwt.coupon.exception.CouponException;
import com.zwt.coupon.template.dao.CouponTemplateDao;
import com.zwt.coupon.template.entity.CouponTemplate;
import com.zwt.coupon.template.service.IAsyncService;
import com.zwt.coupon.template.service.IBuildTemplateService;
import com.zwt.coupon.template.vo.TemplateRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class BuildTemplateServiceImpl implements IBuildTemplateService {

    private final IAsyncService asyncService;
    private final CouponTemplateDao couponTemplateDao;

    @Autowired
    public BuildTemplateServiceImpl(IAsyncService asyncService, CouponTemplateDao couponTemplateDao) {
        this.asyncService = asyncService;
        this.couponTemplateDao = couponTemplateDao;
    }

    @Override
    public CouponTemplate buildTemplate(TemplateRequest request) throws CouponException {

        if (!request.validate()) {
            throw new CouponException("BuildTemplate Param Is Not Valid!");
        }
        if (null != couponTemplateDao.findByName(request.getName())) {
            throw new CouponException("Exist Same Name Template!");
        }

        CouponTemplate template = requestToTemplate(request);
        template = couponTemplateDao.save(template);

        asyncService.asyncConstructCouponByTemplate(template);

        return template;
    }

    /**
     * <h2>将 TemplateRequest 转换为 CouponTemplate</h2>
     * */
    private CouponTemplate requestToTemplate(TemplateRequest request) {
        return new CouponTemplate(
                request.getName(),
                request.getLogo(),
                request.getDesc(),
                request.getCategory(),
                request.getProductLine(),
                request.getCount(),
                request.getUserId(),
                request.getTarget(),
                request.getRule()
        );
    }
}
