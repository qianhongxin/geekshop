/*
 * Copyright (c) 2020 极客之道(daogeek.io).
 * All rights reserved.
 */

package io.geekshop.types.role;

import io.geekshop.types.common.DateOperators;
import io.geekshop.types.common.StringOperators;
import lombok.Data;

/**
 * Created on Nov, 2020 by @author bobo
 */
@Data
public class RoleFilterParameter {
    private StringOperators code;
    private StringOperators description;
    private DateOperators createdAt;
    private DateOperators updatedAt;
}