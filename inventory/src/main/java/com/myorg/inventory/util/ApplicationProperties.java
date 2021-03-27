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

    @Value("inventory.article.articleType.item")
    private String artTypeItem;

    @Value("inventory.article.articleType.product")
    private String artTypeProduct;

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

    public String getArtTypeItem() {
        return artTypeItem;
    }

    public void setArtTypeItem(String artTypeItem) {
        this.artTypeItem = artTypeItem;
    }

    public String getArtTypeProduct() {
        return artTypeProduct;
    }

    public void setArtTypeProduct(String artTypeProduct) {
        this.artTypeProduct = artTypeProduct;
    }
}