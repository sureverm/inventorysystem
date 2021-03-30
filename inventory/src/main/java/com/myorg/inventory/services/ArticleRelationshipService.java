package com.myorg.inventory.services;

import com.google.common.base.Strings;
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
import java.util.*;

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

    /**
     * Here we save the Product as an Article in the database, it goes in the same 'article' table in which Articles are saved.
     * article table's column art_type stores the difference between Article and Product as 'Item' or 'Product'. Product's price is calculated based in Article's price and quantity needed for that Product
     * A relationship table saves the Product and the Articles it contains along with other necessary details.
     * Checks if the Products are already present in db, if yes its updated.
     * While performing the is already present check, have used JPA repository's findByArtNumber(), findByChildArticle_ArtIdAndParentArticle_ArtId(), getOne() which does not make a new db call.
     * Null check for fields significant to our inventory are performed and a placeholder for any notifications to be sent.
     * Kept a placeholder in the event where childArticle mentioned for a Product is not present in db.
     * @param productsList
     */
    @Override
    public void saveArticleRelationship(List<ProductBean> productsList) {
        logger.debug("Inside ArticleRelationshipService saveArticleRelationship(productsList.size())--> size is "+ CollectionUtils.isEmpty(productsList));
        List<ArticleRelationship> articleRelationshipList = new ArrayList<>();
        ArticleRelationship articleRelationship;
        List<Article> articleList = new ArrayList<>();
        Article productArticle;
        Article childArticle;
        Long productPrice = null;
        for (ProductBean productBean : productsList)  {

            if(Strings.isNullOrEmpty(productBean.getProduct_id()) || Strings.isNullOrEmpty(productBean.getName())){
                //other business specific validations which refrains the Product's from being saved in DB
                //TODO place holder to send necessary notification to the concerned Team/Service
                continue;
            }

            productPrice = Long.valueOf(0);
            productArticle = articleRepository.findByArtNumber(productBean.getProduct_id());

            if(null == productArticle)
                productArticle = new Article();

            productArticle.setArtNumber(productBean.getProduct_id());
            productArticle.setName(productBean.getName());
            productArticle.setSellable(productBean.isSellable());
            productArticle.setPrice(productPrice);
            productArticle.setStock(0);
            productArticle.setArtType(applicationProperties.getArtTypeProduct());

            //productArticle = articleRepository.saveAndFlush(productArticle);
            articleList.add(productArticle);

            for(ArticleBean articleBean : CollectionUtils.isEmpty(productBean.getContain_articles()) ? new ArrayList<ArticleBean>() : productBean.getContain_articles()){
                childArticle = articleRepository.findByArtNumber(articleBean.getArtNumber());

                if(null == childArticle) {
                    logger.error("No child inventory/article "+articleBean.getArtNumber()+" found for product: " + productBean.getProduct_id());
                    //throw new RuntimeException("No child "+articleBean.getArtNumber()+" found inventory/article for product: " + productBean.getProduct_id());
                    //TODO place holder to send necessary notification to the concerned Team/Service
                    continue;

                }

                articleRelationship = relationshipRepository.findByChildArticle_ArtIdAndParentArticle_ArtId(childArticle.getArtId(),productArticle.getArtId());
                if(null == articleRelationship)
                    articleRelationship= new ArticleRelationship();

                articleRelationship.setParentArticle(articleRepository.getOne(productArticle.getArtId()));
                articleRelationship.setChildArticle(articleRepository.getOne(childArticle.getArtId()));
                articleRelationship.setQuantity(articleBean.getAmount_of());

                articleRelationshipList.add(articleRelationship);

                productPrice += childArticle.getPrice() * articleRelationship.getQuantity();
            }

            productArticle.setPrice(productPrice);

        }
        articleRepository.saveAll(articleList);
        relationshipRepository.saveAll(articleRelationshipList);

    }

}
