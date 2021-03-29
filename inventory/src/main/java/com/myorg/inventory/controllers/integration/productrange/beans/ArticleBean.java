package com.myorg.inventory.controllers.integration.productrange.beans;

import com.myorg.inventory.models.ArticleRelationship;
import java.util.List;

public class ArticleBean {

    private String artNumber;
    private String artType;
    private String name;
    private Integer stock;
    private Long price;
    private boolean sellable;
    private Integer amount_of;

    public ArticleBean() {
    }

    public ArticleBean(String artNumber, String artType, String name, Integer stock, Long price, boolean sellable, List<ArticleRelationship> listArticleRelationships) {
        this.artNumber = artNumber;
        this.artType = artType;
        this.name = name;
        this.stock = stock;
        this.price = price;
        this.sellable = sellable;

    }

    public String getArtNumber() {
        return artNumber;
    }

    public void setArtNumber(String artNumber) {
        this.artNumber = artNumber;
    }

    public String getArtType() {
        return artType;
    }

    public void setArtType(String artType) {
        this.artType = artType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public boolean isSellable() {
        return sellable;
    }

    public void setSellable(boolean sellable) {
        this.sellable = sellable;
    }

    public Integer getAmount_of() {
        return amount_of;
    }

    public void setAmount_of(Integer amount_of) {
        this.amount_of = amount_of;
    }


}
