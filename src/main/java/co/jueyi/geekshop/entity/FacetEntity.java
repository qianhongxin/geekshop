/*
 * Copyright (c) 2020 掘艺网络(jueyi.co).
 * All rights reserved.
 */

package co.jueyi.geekshop.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * A Facet is a class of properties which can be applied to a {@link co.jueyi.geekshop.types.product.Product}
 * or {@link co.jueyi.geekshop.types.product.ProductVariant}.
 * They are used to enable [faceted search](https://https://en.wikipedia.org/wiki/Faceted_search) whereby products
 * can be filtered along a number of dimensions (facets).
 *
 * For example, there could be a Facet named "Brand" which has a number of
 * {@link co.jueyi.geekshop.types.facet.FacetValue}s representing the various brands of product,
 * e.g. "Apple", "Samsung", "Dell", "HP" etc.
 *
 * Created on Nov, 2020 by @author bobo
 */
@TableName(value = "tb_facet")
@Data
@EqualsAndHashCode(callSuper = true)
public class FacetEntity extends BaseEntity {
    private String name;
    private String code;
    private boolean privateOnly; // private

}
