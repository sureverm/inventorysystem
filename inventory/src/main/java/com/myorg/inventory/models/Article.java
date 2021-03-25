package com.myorg.inventory.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.myorg.inventory.controllers.integration.productrange.beans.ArticleBean;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(name="article")
public class Article implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="art_id")
    private Integer artId;
    @Column(name="art_number")
    private String artNumber;
    @Column(name="art_type")
    private String artType;
    @Column(name="name")
    private String name;
    @Column(name="stock")
    private Integer stock;
    @Column(name="price")
    private Long price;
    @Column(name="sellable")
    private boolean sellable;

    @Transient
    private Integer quantitySold;


    public Article(){

    }

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "art_parent_id",
            referencedColumnName = "art_id"
    )
    private List<ArticleRelationship> listArticleRelationships = new ArrayList<>();

    public Integer getArtId() {
        return artId;
    }

    public void setArtId(Integer artId) {
        this.artId = artId;
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

    public List<ArticleRelationship> getListArticleRelationships() {
        return listArticleRelationships;
    }

    public void setListArticleRelationships(List<ArticleRelationship> listArticleRelationships) {
        this.listArticleRelationships = listArticleRelationships;
    }

    public Integer getQuantitySold() {
        return quantitySold;
    }

    public void setQuantitySold(Integer quantitySold) {
        this.quantitySold = quantitySold;
    }
}
