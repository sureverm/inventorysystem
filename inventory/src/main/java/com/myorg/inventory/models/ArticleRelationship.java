package com.myorg.inventory.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.io.Serializable;


@Entity(name="article_relationship")
public class ArticleRelationship implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="relationship_id")
    private Integer relationshipId;
    @Column(name="quantity")
    private Integer quantity;

    @Transient
    private String childArtNumber;
    @Transient
    private String childArtName;
    @Transient
    private Long childArtPrice;
    @Transient
    private Integer availableInStock;

    public ArticleRelationship(){
    }

    public ArticleRelationship(Integer relationshipId, Integer quantity, Article childArticle, Article parentArticle) {
        this.relationshipId = relationshipId;
        this.quantity = quantity;
        this.childArticle = childArticle;
        this.parentArticle = parentArticle;
    }

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    // EAGER fetch because when returning the sellable product list, new database calls must not be made when fetching the relationships
    @JoinColumn(
            name = "art_child_id",
            referencedColumnName = "art_id"
    )
    private Article childArticle;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    // EAGER fetch because when returning the sellable product list, new database calls must not be made when fetching the relationships
    @JoinColumn(
            name = "art_parent_id",
            referencedColumnName = "art_id"
    )
    private Article parentArticle;

    public Integer getRelationshipId() {
        return relationshipId;
    }

    public void setRelationshipId(Integer relationshipId) {
        this.relationshipId = relationshipId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getChildArtNumber() {
        return childArtNumber;
    }

    public void setChildArtNumber(String childArtNumber) {
        this.childArtNumber = childArtNumber;
    }

    public String getChildArtName() {
        return childArtName;
    }

    public void setChildArtName(String childArtName) {
        this.childArtName = childArtName;
    }

    public Long getChildArtPrice() {
        return childArtPrice;
    }

    public void setChildArtPrice(Long childArtPrice) {
        this.childArtPrice = childArtPrice;
    }

    public Integer getAvailableInStock() {
        return availableInStock;
    }

    public void setAvailableInStock(Integer availableInStock) {
        this.availableInStock = availableInStock;
    }

    public Article getChildArticle() {
        return childArticle;
    }

    public void setChildArticle(Article childArticle) {
        this.childArticle = childArticle;
    }

    public Article getParentArticle() {
        return parentArticle;
    }

    public void setParentArticle(Article parentArticle) {
        this.parentArticle = parentArticle;
    }
}
