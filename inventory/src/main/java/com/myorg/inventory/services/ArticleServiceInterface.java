package com.myorg.inventory.services;

import com.myorg.inventory.controllers.integration.productrange.beans.ArticleBean;
import com.myorg.inventory.controllers.integration.productrange.beans.Articles;
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

    List<ArticleDBView> listAllFromView(Map<String,String> qparams);

    void save(List<ArticleBean> articleList);

    Article saveAndFlush(Article article);

    Article getArticle(String art_id);

    String updateArticleStock(Articles articles);
}
