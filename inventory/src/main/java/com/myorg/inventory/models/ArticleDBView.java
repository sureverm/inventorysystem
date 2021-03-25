package com.myorg.inventory.models;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "articles_view")
public class ArticleDBView implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="UUID")
    private String id;

    @Column(name="art_parent_id")
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

    public ArticleDBView(){

    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "art_id",
            referencedColumnName = "art_id"
    )
    private Article article;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
}
