package com.myorg.inventory.services;

import com.google.common.base.Strings;
import com.myorg.inventory.controllers.beans.SoldProducts;
import com.myorg.inventory.controllers.beans.SoldProductsOrderDetails;
import com.myorg.inventory.controllers.integration.productrange.beans.ArticleBean;
import com.myorg.inventory.controllers.integration.productrange.beans.InventoryResponse;
import com.myorg.inventory.models.Article;
import com.myorg.inventory.models.ArticleRelationship;
import com.myorg.inventory.repositories.ArticleRepository;
import com.myorg.inventory.util.ApplicationProperties;
import com.myorg.inventory.util.PublishNotifications;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class ArticleService implements ArticleServiceInterface{
    private static final Logger logger = LogManager.getLogger(ArticleService.class);

    @Autowired
    ApplicationProperties applicationProperties;

    @Autowired
    private ArticleRepository repository;

    @Override
    public List<Article> listAll(Map<String,String> qparams) {
        /*
        TODO filter records based on query params from URI
         Send parametrised query and fetch records
         */
        logger.debug("Inside ArticleService listAll(Map<String,String> qparams)--> query parameters isNull ? "+ CollectionUtils.isEmpty(qparams));
        List<Article> parentArticles = repository.findAll();
        for(Article article : parentArticles){
            for (ArticleRelationship relationship :  article.getListArticleRelationships()) {
                relationship.setChildArtName(relationship.getChildArticle().getName());
                relationship.setChildArtNumber(relationship.getChildArticle().getArtNumber());
                relationship.setChildArtPrice(relationship.getChildArticle().getPrice());
                relationship.setAvailableInStock(relationship.getChildArticle().getStock());
            }
        }
        return parentArticles; //repository.fetchProducts();

    }


    @Override
    public List<Article> save(List<ArticleBean> articleBeanList) {
        logger.debug("Inside ArticleService save(articleBeanList)--> is input list null ? "+ CollectionUtils.isEmpty(articleBeanList));
        List<Article> articleList = new ArrayList<>();
        Article article;
        for (ArticleBean articleBean : articleBeanList)  {
            article = repository.findByArtNumber(articleBean.getArtNumber());

            if(null == article)
                article = new Article();

            if(Strings.isNullOrEmpty(articleBean.getArtNumber()) || Strings.isNullOrEmpty(articleBean.getName())){
                //other business specific validations which refrains the Article from being saved in DB
                // TODO place holder to send necessary notification to the concerned Team/Service
                continue;
            }

            article.setArtNumber(articleBean.getArtNumber());
            article.setName(articleBean.getName());
            article.setStock(articleBean.getStock());
            article.setPrice(articleBean.getPrice());
            article.setSellable(articleBean.isSellable());
            article.setArtType(applicationProperties.getArtTypeItem());

            articleList.add(article);
        }
        return repository.saveAll(articleList);

    }

    @Override
    public Article getArticle(String art_id) {
        return repository.findByArtNumber(art_id);
    }

    @Override
    public InventoryResponse updateArticleStock(SoldProductsOrderDetails soldProductsOrderDetails) {
        logger.debug("Inside ArticleService updateArticleStock(soldProductsOrderDetails)--> is input list null ? "+ CollectionUtils.isEmpty(soldProductsOrderDetails.getSoldProduclsList()));

        //TODO if we consider multiple orders will be send in a single payload for stock update then, validations needs to be placed here
        //The consideration here is one order's sold product's details are being sent as payload

        Article parentArticleFromDB, childArticleToUpdate;
        Integer countToSubtract;
        InventoryResponse returnObject = new InventoryResponse();

        for (SoldProducts parentArticleSold : soldProductsOrderDetails.getSoldProduclsList())  {

            parentArticleFromDB = repository.findByArtNumber(parentArticleSold.getArtNumber());

            if(null == parentArticleFromDB)
                throw new InputMismatchException("SoldProduct's article number "+ parentArticleSold.getArtNumber() +" not found in the inventory");

            for(ArticleRelationship relationship : parentArticleFromDB.getListArticleRelationships()){

                childArticleToUpdate = repository.findByArtNumber(relationship.getChildArticle().getArtNumber());
                if(null == childArticleToUpdate)
                    throw new InputMismatchException("SoldProduct's Child article number "+ relationship.getChildArticle().getArtNumber() +" not found in the inventory");

                countToSubtract = Integer.valueOf(parentArticleSold.getQuantitySold()) * Integer.valueOf(relationship.getQuantity());

                if(countToSubtract > childArticleToUpdate.getStock()) {
                    PublishNotifications.sendNotificationToDownstreams("SystemXX","Stock status not enough to fulfill item xxx from orderId xxx");//placeholder
                    throw new IllegalStateException("Contact System Administrator!! For SoldProduct's article number " + parentArticleSold.getArtNumber() + " -- " +
                            "and Child article number " + relationship.getChildArticle().getArtNumber() + " - stock status not enough");
                }
                childArticleToUpdate.setStock(childArticleToUpdate.getStock()-countToSubtract);
                logger.debug("Child Article value updated for articlenumber - "+relationship.getChildArticle().getArtNumber()+" to "+(childArticleToUpdate.getStock()));

                repository.saveAndFlush(childArticleToUpdate);//the stock quantity of Child article must be updated in DB for next Child article's stock update
            }

        }
        returnObject.setOrderId(soldProductsOrderDetails.getOrderId());
        returnObject.setOrderNumber(soldProductsOrderDetails.getOrderNumber());
        returnObject.setStatusMessage("Stock Updated successfully.");
        return returnObject;
    }

}
