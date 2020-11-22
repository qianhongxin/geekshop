/*
 * Copyright (c) 2020 掘艺网络(jueyi.co).
 * All rights reserved.
 */

package co.jueyi.geekshop.resolver.dataloader;

import co.jueyi.geekshop.common.utils.BeanMapper;
import co.jueyi.geekshop.entity.ProductOptionGroupEntity;
import co.jueyi.geekshop.mapper.ProductOptionGroupEntityMapper;
import co.jueyi.geekshop.types.product.ProductOptionGroup;
import org.dataloader.MappedBatchLoader;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

/**
 * Created on Nov, 2020 by @author bobo
 */
public class ProductOptionGroupDataLoader implements MappedBatchLoader<Long, ProductOptionGroup> {

    private final ProductOptionGroupEntityMapper productOptionGroupEntityMapper;

    public ProductOptionGroupDataLoader(ProductOptionGroupEntityMapper productOptionGroupEntityMapper) {
        this.productOptionGroupEntityMapper = productOptionGroupEntityMapper;
    }

    @Override
    public CompletionStage<Map<Long, ProductOptionGroup>> load(Set<Long> groupIds) {
        return CompletableFuture.supplyAsync(() -> {
            List<ProductOptionGroupEntity> productOptionGroupEntities =
                    this.productOptionGroupEntityMapper.selectBatchIds(groupIds);
            List<ProductOptionGroup> productOptionGroups = productOptionGroupEntities.stream()
                    .map(optionGroupEntity -> BeanMapper.map(optionGroupEntity, ProductOptionGroup.class))
                    .collect(Collectors.toList());
            Map<Long, ProductOptionGroup> optionGroupMap = productOptionGroups.stream()
                    .collect(Collectors.toMap(ProductOptionGroup::getId, group -> group));
            return optionGroupMap;
        });
    }
}
