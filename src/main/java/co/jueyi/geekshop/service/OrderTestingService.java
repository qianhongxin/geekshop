/*
 * Copyright (c) 2020 掘艺网络(jueyi.co).
 * All rights reserved.
 */

package co.jueyi.geekshop.service;

import co.jueyi.geekshop.common.utils.BeanMapper;
import co.jueyi.geekshop.config.shipping_method.ShippingCalculationResult;
import co.jueyi.geekshop.entity.*;
import co.jueyi.geekshop.mapper.ProductVariantEntityMapper;
import co.jueyi.geekshop.service.helpers.ServiceHelper;
import co.jueyi.geekshop.service.helpers.ShippingConfiguration;
import co.jueyi.geekshop.service.helpers.order_calculator.OrderCalculator;
import co.jueyi.geekshop.service.helpers.shipping_calculator.EligibleShippingMethod;
import co.jueyi.geekshop.service.helpers.shipping_calculator.ShippingCalculator;
import co.jueyi.geekshop.types.common.CreateAddressInput;
import co.jueyi.geekshop.types.order.OrderAddress;
import co.jueyi.geekshop.types.shipping.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This service is responsible for creating temporary mock Orders which tests can be run, such as
 * testing a ShippingMethod or Promotion.
 *
 * Created on Dec, 2020 by @author bobo
 */
@Service
@RequiredArgsConstructor
public class OrderTestingService {
    private final OrderCalculator orderCalculator;
    private final ShippingCalculator shippingCalculator;
    private final ShippingConfiguration shippingConfiguration;
    private final ProductVariantEntityMapper productVariantEntityMapper;

    /**
     * Runs a given ShippingMethod configuration against a mock Order to test for eligibility and resulting
     * price.
     */
    public TestShippingMethodResult testShippingMethod(TestShippingMethodInput input) {
        ShippingMethodEntity shippingMethod = new ShippingMethodEntity();
        shippingMethod.setChecker((this.shippingConfiguration).parseCheckerInput(input.getChecker()));
        shippingMethod.setCalculator(this.shippingConfiguration.parseCalculatorInput(input.getCalculator()));

        OrderEntity mockOrder = this.buildMockOrder(input.getShippingAddress(), input.getLines());

        boolean eligible = shippingMethod.test(mockOrder);
        ShippingCalculationResult shippingCalculationResult = eligible ? shippingMethod.apply(mockOrder) : null;

        TestShippingMethodResult testShippingMethodResult = new TestShippingMethodResult();
        testShippingMethodResult.setEligible(eligible);
        TestShippingMethodQuote quote = new TestShippingMethodQuote();
        if (shippingCalculationResult != null) {
            quote.setPrice(shippingCalculationResult.getPrice());
            quote.setMetadata(shippingCalculationResult.getMetadata());
        }
        quote.setDescription(shippingMethod.getDescription());
        testShippingMethodResult.setQuote(quote);

        return testShippingMethodResult;
    }

    /**
     * Tests all available ShippingMethods against a mock Order and return those which are eligible. This
     * is intended to simulate a call to the `eligibleShippingMethods` query of the Shop API.
     */
    public List<ShippingMethodQuote> testEligibleShippingMethods(TestEligibleShippingMethodsInput input) {
        OrderEntity mockOrder = this.buildMockOrder(input.getShippingAddress(), input.getLines());
        List<EligibleShippingMethod>  eligibleMethods = this.shippingCalculator.getEligibleShippingMethods(mockOrder);
        return eligibleMethods.stream().map(m -> {
                    ShippingMethodQuote quote = new ShippingMethodQuote();
                    quote.setId(m.getMethod().getId());
                    quote.setPrice(m.getResult().getPrice());
                    quote.setDescription(m.getMethod().getDescription());
                    quote.setMetadata(m.getResult().getMetadata());
                    return quote;
                }).collect(Collectors.toList());
    }

    private OrderEntity buildMockOrder(CreateAddressInput shippingAddress, List<TestShippingMethodOrderLineInput> lines) {
        OrderEntity mockOrder = new OrderEntity();
        OrderAddress shippingOrderAddress = BeanMapper.map(shippingAddress, OrderAddress.class);
        mockOrder.setShippingAddress(shippingOrderAddress);
        for(TestShippingMethodOrderLineInput line : lines) {
            ProductVariantEntity productVariant = ServiceHelper.getEntityOrThrow(
                            this.productVariantEntityMapper,
                            ProductVariantEntity.class,
                            line.getProductVariantId()
                    );
            OrderLineEntity orderLine = new OrderLineEntity();
            orderLine.setProductVariantId(productVariant.getId());
            mockOrder.getLines().add(orderLine);

            for (int i = 0; i < line.getQuantity(); i++) {
                OrderItemEntity orderItem = new OrderItemEntity();
                orderItem.setUnitPrice(productVariant.getPrice());
                orderLine.getItems().add(orderItem);
            }
        }
        this.orderCalculator.applyPriceAdjustments(mockOrder, Arrays.asList());
        return mockOrder;
    }
}
