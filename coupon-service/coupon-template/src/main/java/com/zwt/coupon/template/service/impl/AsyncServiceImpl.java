package com.zwt.coupon.template.service.impl;

import com.google.common.base.Stopwatch;
import com.zwt.coupon.constant.Constant;
import com.zwt.coupon.template.dao.CouponTemplateDao;
import com.zwt.coupon.template.entity.CouponTemplate;
import com.zwt.coupon.template.service.IAsyncService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@Slf4j
@Service
public class AsyncServiceImpl implements IAsyncService {

    private static final String TAG = "[aTang][AsyncServiceImpl]";

    private final CouponTemplateDao templateDao;
    private final StringRedisTemplate redisTemplate;

    public AsyncServiceImpl(CouponTemplateDao templateDao, StringRedisTemplate redisTemplate) {
        this.templateDao = templateDao;
        this.redisTemplate = redisTemplate;
    }

    @Override
    @Async("getAsyncExecutor")
    @SuppressWarnings("all")
    public void asyncConstructCouponByTemplate(CouponTemplate template) {
        Stopwatch watch = Stopwatch.createStarted();

        Set<String> couponCodeSet = buildCouponCode(template);
        String redisKey = String.format("%s%s", Constant.RedisPrefix.COUPON_TEMPLATE, template.getId().toString());
        log.info(TAG + "Push count of coupon code to Redis is : {}!", redisTemplate.opsForList().rightPushAll(redisKey, couponCodeSet));
        template.setAvailable(true);
        templateDao.save(template);

        watch.stop();
        log.info(TAG + "Construct Coupon Code By Template Cost: {}ms", watch.elapsed(TimeUnit.MILLISECONDS));
        log.info(TAG + "CouponTemplate_{} is Available!", template.getId());
    }

    /**
     * 生成优惠券码
     * 前四位：产品线+类型，中间六位：日期220622，后八位：随机数
     * @param template 实体类
     * @return 与template.count数量相同的优惠券🐎
     */
    @SuppressWarnings("all")
    private Set<String> buildCouponCode(CouponTemplate template) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        Set<String> result = new HashSet<>(template.getCount());
        StringBuilder builder = new StringBuilder();

        builder.append(template.getProductLine().getCode().toString());
        builder.append(template.getCategory().getCode());
        String date = new SimpleDateFormat("yyMMdd").format(template.getCreateTime());

        for (int i = 0;i < template.getCount();i++) {
            result.add(builder.toString() + buildCouponCodeSuffix14(date));
        }
        while (result.size() < template.getCount()) {
            result.add(builder.toString() + buildCouponCodeSuffix14(date));
        }

        stopwatch.stop();
        log.info(TAG + "Build Coupon Code Cost: {}ms", stopwatch.elapsed(TimeUnit.MILLISECONDS));
        return result;
    }

    private String buildCouponCodeSuffix14(String date) {
        char[] bases = new char[]{'1', '2', '3', '4', '5', '6','7', '8', '9'};

        List<Character> chars = date.chars().mapToObj(e -> (char)e).collect(Collectors.toList());
        Collections.shuffle(chars);
        StringBuilder builder = new StringBuilder();
        String mid6 = chars.stream().map(Objects::toString).collect(Collectors.joining());
        builder.append(mid6);
        builder.append(RandomStringUtils.random(1, bases));
        builder.append(RandomStringUtils.randomNumeric(7));

        return builder.toString();
    }
}
