package com.myorg.inventory.controllers.integration.productrange.beans;

import com.myorg.inventory.models.ArticleRelationship;


import java.util.List;

public class ArticleBean {

    private String art_id;
    private String artType;
    private String name;
    private Integer stock;
    private Long price;
    private boolean sellable;
    private Integer amount_of;
    private Integer quantitySold;
    private String artNumber;
    private List<ArticleRelationship> listArticleRelationships;

    public String getArt_id() {
        return art_id;
    }

    public ArticleBean() {
    }

    public ArticleBean(String art_id, String artType, String name, Integer stock, Long price, boolean sellable, String artNumber, List<ArticleRelationship> listArticleRelationships) {
        this.art_id = art_id;
        this.artType = artType;
        this.name = name;
        this.stock = stock;
        this.price = price;
        this.sellable = sellable;
        this.artNumber = artNumber;
        this.listArticleRelationships = listArticleRelationships;
    }

    public void setArt_id(String art_id) {
        this.art_id = art_id;
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

    public Integer getQuantitySold() {
        return quantitySold;
    }

    public void setQuantitySold(Integer quantitySold) {
        this.quantitySold = quantitySold;
    }

    public List<ArticleRelationship> getListArticleRelationships() {
        return listArticleRelationships;
    }

    public void setListArticleRelationships(List<ArticleRelationship> listArticleRelationships) {
        this.listArticleRelationships = listArticleRelationships;
    }

    public String getArtNumber() {
        return artNumber;
    }

    public void setArtNumber(String artNumber) {
        this.artNumber = artNumber;
    }
}
