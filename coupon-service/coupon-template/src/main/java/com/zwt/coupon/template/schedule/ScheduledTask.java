package com.zwt.coupon.template.schedule;

import com.zwt.coupon.template.dao.CouponTemplateDao;
import com.zwt.coupon.template.entity.CouponTemplate;
import com.zwt.coupon.vo.TemplateRule;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class ScheduledTask {
    private static final String TAG = "[aTang][ScheduledTask]";

    private final CouponTemplateDao templateDao;

    @Autowired
    public ScheduledTask(CouponTemplateDao templateDao) {
        this.templateDao = templateDao;
    }

    @Scheduled(fixedRate = 60 * 60 *24)
    public void offlineCouponTemplate() {
        List<CouponTemplate> templates = templateDao.findAllByExpired(false);
        if (CollectionUtils.isEmpty(templates)) {
            log.info(TAG + "all coupon template are expired!");
            return;
        }
        Date curTime = new Date();
        List<CouponTemplate> expiredTemplates = new ArrayList<>();
        templates.forEach(t -> {
            TemplateRule rule = t.getRule();
            if (rule.getExpiration().getDeadline() < curTime.getTime()) {
                t.setExpired(true);
                expiredTemplates.add(t);
            }
        });
        if (CollectionUtils.isNotEmpty(expiredTemplates)) {
            log.info(TAG + "expired coupon template Num: {}", templateDao.saveAll(expiredTemplates));
        }
        log.info(TAG + "done to expire coupon template");
    }
}
