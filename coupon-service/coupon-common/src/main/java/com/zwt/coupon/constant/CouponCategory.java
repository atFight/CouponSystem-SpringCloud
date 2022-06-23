package com.zwt.coupon.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum CouponCategory {

    FULL_REDUCTION("满减券", "001"),
    DISCOUNT("折扣券", "002"),
    REDUCE_IMMEDIATELY("立减券", "003");

    private final String description;

    private final String code;

    public static CouponCategory of(String code) {
        Objects.requireNonNull(code);
        return Stream.of(values())
                .filter(bean -> bean.code.equals(code))
                .findAny()
                .orElseThrow(()  -> new  IllegalArgumentException(code + " not exists!"));
    }
}
