package com.zwt.coupon.vo;

import com.zwt.coupon.constant.PeriodType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TemplateRule {

    private Expiration expiration;
    private Discount discount;
    /* 每个人最多领几张 */
    private Integer limitation;
    private Usage usage;
    /* 权重，可以和哪些优惠券叠加使用 */
    private String weight;

    public boolean validate() {
        return expiration.validate() && discount.validate() && limitation > 0 && usage.validate() && StringUtils.isNoneEmpty(weight);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Expiration {    /* 有效期规则 */
        /* 有效期规则，对应PeriodType的code字段 */
        private Integer period;
        /* 有效间隔：只对变动性有效期有效 */
        private Integer gap;
        /* 优惠券模板的失效日期，两类规则都有效 */
        private Long deadline;

        boolean validate() {
            return PeriodType.of(this.period) != null && gap > 0 && deadline > 0;
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Discount {    /* 折扣规则 */
        /* 额度：满减-20， 则扣 85%， 立减-10 */
        private Integer quota;
        /* 基准，需要满多少才能用 */
        private Integer base;


        boolean validate() {
            return this.quota > 0 && this.base > 0;
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Usage {    /* 使用范围 */
        /* 省份 */
        private String province;
        /* 城市 */
        private String city;
        /* 商品类型 */
        private String goodsType;

        boolean validate() {
            return StringUtils.isNoneEmpty(this.province)
                    && StringUtils.isNoneEmpty(this.city)
                    && StringUtils.isNoneEmpty(this.goodsType);
        }
    }

}
