/*
 * Copyright (c) 2020 掘艺网络(jueyi.co).
 * All rights reserved.
 */

package co.jueyi.geekshop.entity;

import co.jueyi.geekshop.custom.mybatis_plus.AdjustmentListTypeHandler;
import co.jueyi.geekshop.types.common.Adjustment;
import co.jueyi.geekshop.types.common.AdjustmentType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An individual item of an {@link co.jueyi.geekshop.types.order.OrderLine}
 *
 * Created on Dec, 2020 by @author bobo
 */
@TableName(value = "tb_order_item", autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
public class OrderItemEntity extends BaseEntity {
    private Long orderLineId;
    private Integer unitPrice;
    @TableField(typeHandler = AdjustmentListTypeHandler.class)
    private List<Adjustment> pendingAdjustments = new ArrayList<>();
    private Long fulfillmentId;
    private Long refundId;
    private Long cancellationId;
    private boolean cancelled;

    public List<Adjustment> getAdjustments() {
        return this.pendingAdjustments;
    }

    public Integer getPromotionAdjustmentsTotal() {
        return this.getAdjustments().stream().filter(adjustment -> adjustment.getType() == AdjustmentType.PROMOTION)
                .reduce(0, (total, a) -> total + a.getAmount(), Integer::sum);
    }

    /**
     * This is the actual, final price of the OrderItem payable by the customer.
     */
    public Integer getUnitPriceWithPromotions() {
        return this.unitPrice + this.getPromotionAdjustmentsTotal();
    }

    public void clearAdjustments(AdjustmentType type) {
        if (type == null) {
            this.pendingAdjustments = new ArrayList<>();
        } else {
            this.pendingAdjustments = this.pendingAdjustments.stream()
                    .filter(a -> a.getType() != type).collect(Collectors.toList());
        }
    }
}
