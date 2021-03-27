package com.myorg.inventory.services;

import com.myorg.inventory.controllers.integration.productrange.beans.ArticleBean;
import com.myorg.inventory.controllers.integration.productrange.beans.ProductBean;
import com.myorg.inventory.models.Article;
import com.myorg.inventory.models.ArticleRelationship;
import com.myorg.inventory.repositories.ArticleRelationshipRepository;
import com.myorg.inventory.repositories.ArticleRepository;
import com.myorg.inventory.util.ApplicationProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class ArticleRelationshipService implements ArticleRelationshipServiceInterface{

    private static final Logger logger = LogManager.getLogger(ArticleRelationshipService.class);

    @Autowired
    ApplicationProperties applicationProperties;

    @Autowired
    private ArticleRelationshipRepository relationshipRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public List<ArticleRelationship> listAll() {
        return relationshipRepository.findAll();
    }

    @Override
    public void saveArticleRelationship(List<ProductBean> productsList) {
        logger.debug("Inside ArticleRelationshipService saveArticleRelationship(productsList.size())--> size is "+ CollectionUtils.isEmpty(productsList));
        List<ArticleRelationship> articleRelationshipList = new ArrayList<>();
        ArticleRelationship articleRelationship;
        List<Article> articleList = new ArrayList<>();
        Article article;
        Article childArticle;
        Long productPrice = null;
        for (ProductBean productBean : productsList)  {
            productPrice = Long.valueOf(0);
            article = articleRepository.findByArtNumber(productBean.getProduct_id());

            if(null == article)
                article = new Article();

            article.setArtNumber(productBean.getProduct_id());
            article.setName(productBean.getName());
            article.setSellable(productBean.isSellable());
            article.setPrice(productPrice);
            article.setStock(0);
            article.setArtType(applicationProperties.getArtTypeProduct());

            article = articleRepository.saveAndFlush(article);
            articleList.add(article);

            for(ArticleBean articleBean : productBean.getContain_articles()){
                childArticle = articleRepository.findByArtNumber(articleBean.getArt_id());

                if(null == childArticle) {
                    logger.error("No inventory for product: " + productBean.getProduct_id());
                    throw new RuntimeException("No inventory for product: " + productBean.getProduct_id());
                }

                articleRelationship = relationshipRepository.findByChildArticle_ArtIdAndParentArticle_ArtId(childArticle.getArtId(),article.getArtId());
                if(null == articleRelationship)
                    articleRelationship= new ArticleRelationship();

                articleRelationship.setParentArticle(articleRepository.getOne(article.getArtId()));
                articleRelationship.setChildArticle(articleRepository.getOne(childArticle.getArtId()));
                articleRelationship.setQuantity(articleBean.getAmount_of());

                articleRelationshipList.add(articleRelationship);

                productPrice += childArticle.getPrice() * articleRelationship.getQuantity();
            }

            article.setPrice(productPrice);

        }
        articleRepository.saveAll(articleList);
        relationshipRepository.saveAll(articleRelationshipList);

    }

}
