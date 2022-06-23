package com.zwt.coupon.template.controller;

import com.alibaba.fastjson.JSON;
import com.zwt.coupon.exception.CouponException;
import com.zwt.coupon.template.entity.CouponTemplate;
import com.zwt.coupon.template.service.IBuildTemplateService;
import com.zwt.coupon.template.service.ITemplateBaseService;
import com.zwt.coupon.template.vo.TemplateRequest;
import com.zwt.coupon.vo.CouponTemplateSDK;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class CouponTemplateController {
    private static final String TAG = "[aTang][CouponTemplateController]";
    /** 构建优惠券模板服务 */
    private final IBuildTemplateService buildTemplateService;
    /** 优惠券模板基础服务 */
    private final ITemplateBaseService templateBaseService;

    @Autowired
    public CouponTemplateController(IBuildTemplateService buildTemplateService, ITemplateBaseService templateBaseService) {
        this.buildTemplateService = buildTemplateService;
        this.templateBaseService = templateBaseService;
    }

    @PostMapping("/template/build")
    public CouponTemplate buildTemplate(@RequestBody TemplateRequest request) throws CouponException {
        log.info(TAG + "build template: {}", JSON.toJSON(request));
        return buildTemplateService.buildTemplate(request);
    }

    /**
     * <h2>查找所有可用的优惠券模板</h2>
     * 127.0.0.1:7001/coupon-template/template/sdk/all
     * gateway route: 127.0.0.1:9000/imooc/coupon-template/template/sdk/all
     * */
    @GetMapping("/template/sdk/all")
    public List<CouponTemplateSDK> findAllUsableTemplate() {
        log.info(TAG + "Find All Usable Template.");
        return templateBaseService.findAllUsableTemplate();
    }

    /**
     * <h2>获取模板 ids 到 CouponTemplateSDK 的映射</h2>
     * 127.0.0.1:7001/coupon-template/template/sdk/infos
     * gateway route: 127.0.0.1:9000/imooc/coupon-template/template/sdk/infos
     * */
    @GetMapping("/template/sdk/infos")
    public Map<Integer, CouponTemplateSDK> findIds2TemplateSDK(@RequestParam("ids") Collection<Integer> ids) {
        log.info(TAG + "FindIds2TemplateSDK: {}", JSON.toJSONString(ids));
        return templateBaseService.findIds2TemplateSDK(ids);
    }
}
