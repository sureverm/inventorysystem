package com.myorg.inventory.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
//@PropertySource("classpath:integration.properties")
@ConfigurationProperties(prefix = "integ")
public class ApplicationProperties {

    @Value("${integ.productrangeapp.inventory.url}")
    private String productRangeAppInventoryUrl;

    @Value("${integ.productrangeapp.products.url}")
    private String productRangeAppProductsUrl;

    public String getProductRangeAppInventoryUrl() {
        return productRangeAppInventoryUrl;
    }

    public void setProductRangeAppInventoryUrl(String productRangeAppInventoryUrl) {
        this.productRangeAppInventoryUrl = productRangeAppInventoryUrl;
    }

    public String getProductRangeAppProductsUrl() {
        return productRangeAppProductsUrl;
    }

    public void setProductRangeAppProductsUrl(String productRangeAppProductsUrl) {
        this.productRangeAppProductsUrl = productRangeAppProductsUrl;
    }
}