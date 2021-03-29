package com.myorg.inventory.services;

import com.myorg.inventory.controllers.beans.SoldProductsOrderDetails;
import com.myorg.inventory.controllers.integration.productrange.beans.ArticleBean;
import com.myorg.inventory.controllers.integration.productrange.beans.Articles;
import com.myorg.inventory.controllers.integration.productrange.beans.InventoryResponse;
import com.myorg.inventory.models.Article;
import com.myorg.inventory.models.ArticleDBView;
import com.myorg.inventory.models.ArticleRelationship;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public interface ArticleServiceInterface {

    List<Article> listAll(Map<String,String> qparams);

    List<Article> save(List<ArticleBean> articleList);

    Article getArticle(String art_id);

    InventoryResponse updateArticleStock(SoldProductsOrderDetails soldProductsOrderDetails);
}
