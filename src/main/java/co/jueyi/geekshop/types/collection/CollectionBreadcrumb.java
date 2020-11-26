/*
 * Copyright (c) 2020 掘艺网络(jueyi.co).
 * All rights reserved.
 */

package co.jueyi.geekshop.types.collection;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created on Nov, 2020 by @author bobo
 */
@Data
@AllArgsConstructor
public class CollectionBreadcrumb {
    private Long id;
    private String name;
    private String slug;
}
