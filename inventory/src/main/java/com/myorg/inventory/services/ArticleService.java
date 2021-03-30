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

    /**
     * This method returns list of sellable Products which are available in the inventory database.
     * If there are no sellable products returned from the db, an error response is returned with status 200 Ok, this can be changed to a successful message response as needed.A placeholder for any notifications to be sent to concerned systems.
     * The Product's relationships are fetched and then child article's details are updated in the response object. Since Articles and Products are saved in the same db table, EAGER fetch of dependencies in JPA will cause circular dependency.
     * @param qparams PlaceHolder for any filter options when returning the list of sellable Products.
     * @return returns list of sellable Products which are available in the inventory database.
     */
    @Override
    public List<Article> listAll(Map<String,String> qparams) {
        /*
        TODO filter records based on query params from URI
         Send parametrised query and fetch records
         */
        logger.debug("Inside ArticleService listAll(Map<String,String> qparams)--> query parameters isNull ? "+ CollectionUtils.isEmpty(qparams));
        List<Article> parentArticles = repository.findAll();
        if(CollectionUtils.isEmpty(parentArticles))
            throw new NoSuchElementException("No sellable Products found at the moment.");
            //TODO notifications sent to concerned team

        for(Article article : parentArticles){
            for (ArticleRelationship relationship :  article.getListArticleRelationships()) {
                if(null == relationship)
                    continue;
                relationship.setChildArtName(relationship.getChildArticle().getName());
                relationship.setChildArtNumber(relationship.getChildArticle().getArtNumber());
                relationship.setChildArtPrice(relationship.getChildArticle().getPrice());
                relationship.setAvailableInStock(relationship.getChildArticle().getStock());
            }
        }
        return parentArticles; //repository.fetchProducts();

    }

    /**
     * This method saves the Article details in DB. Checks if the articles are already present in db, if yes its updated.
     * While performing the is already present check, have used JPA repository's findByArtNumber() which does not make a new db call.
     * Null check for fields significant to our inventory are performed and a placeholder for any notifications to be sent.
     * @param articleBeanList List of articles into ArticleBean object mapped from the incoming json format
     * @return List of saves articles, for Mocikto test cases
     */
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

    /**
     * This method updates the stock status based on incoming request when Products are sold be Selling service. It is assumed that only one Order detail's payload will be sent to this method.
     * Before the update, checks are performed if the Product is present in db, child articles are present and an error response is sent if any of them are not found.
     * If the child article's stock is not enough error response to the user is sent and a placeholder is created PublishNotifications.sendNotificationToDownstreams() to notify concerned services.
     * While performing the is already present check, have used JPA repository's findByArtNumber(), which does not make a new db call.
     * Kept a placeholder in the event where childArticle mentioned for a Product is not present in db.
     * @param soldProductsOrderDetails
     * @return
     */
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
