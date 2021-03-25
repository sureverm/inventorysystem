package com.myorg.inventory.controllers.integration.productrange.beans;

import java.util.List;

public class ProductBean {

    private String product_id;
    private String name;
    private boolean sellable;
    private List<ArticleBean> contain_articles;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSellable() {
        return sellable;
    }

    public void setSellable(boolean sellable) {
        this.sellable = sellable;
    }

    public List<ArticleBean> getContain_articles() {
        return contain_articles;
    }

    public void setContain_articles(List<ArticleBean> contain_articles) {
        this.contain_articles = contain_articles;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }
}
