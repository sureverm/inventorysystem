package com.myorg.inventory.services;

import com.myorg.inventory.controllers.integration.productrange.beans.ProductBean;
import com.myorg.inventory.models.ArticleRelationship;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public interface ArticleRelationshipServiceInterface {

    List<ArticleRelationship> listAll();

    void saveArticleRelationship(List<ProductBean> productsList);

}
