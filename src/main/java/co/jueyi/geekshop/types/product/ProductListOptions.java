/*
 * Copyright (c) 2020 掘艺网络(jueyi.co).
 * All rights reserved.
 */

package co.jueyi.geekshop.types.product;

import co.jueyi.geekshop.types.common.ListOptions;
import lombok.Data;

/**
 * Created on Nov, 2020 by @author bobo
 */
@Data
public class ProductListOptions implements ListOptions {
    private Integer currentPage;
    private Integer pageSize;
    private ProductSortParameter sort;
    private ProductFilterParameter filter;
}
