package com.myorg.inventory.controllers.integration.productrange.beans;

import com.myorg.inventory.models.Article;

import java.util.List;

public class Articles {

    private List<ArticleBean> inventory;

    public List<ArticleBean> getInventory() {
        return inventory;
    }

    public void setInventory(List<ArticleBean> inventory) {
        this.inventory = inventory;
    }

}
