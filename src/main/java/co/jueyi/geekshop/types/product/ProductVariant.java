/*
 * Copyright (c) 2020 掘艺网络(jueyi.co).
 * All rights reserved.
 */

package co.jueyi.geekshop.types.product;

import co.jueyi.geekshop.types.asset.Asset;
import co.jueyi.geekshop.types.common.Node;
import co.jueyi.geekshop.types.facet.FacetValue;
import co.jueyi.geekshop.types.stock.StockMovementList;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created on Nov, 2020 by @author bobo
 */
@Data
public class ProductVariant implements Node {
    private Long id;
    private Product product;
    private Long productId;
    private Date createdAt;
    private Date updatedAt;
    private String sku;
    private String name;
    // 该字段对GraphQL不可见，仅内部使用
    private Long featuredAssetId;
    private Asset featuredAsset;
    private List<Asset> assets = new ArrayList<>();
    private Integer price;
    private List<ProductOption> options = new ArrayList<>();
    private List<FacetValue> facetValues = new ArrayList<>();
    private Boolean enabled; // admin only
    private Integer stockOnHand; // admin only
    private Boolean trackInventory; // admin only
    private StockMovementList stockMovements; // admin only
}
