package com.myorg.inventory.services;

import com.myorg.inventory.controllers.integration.productrange.beans.*;
import com.myorg.inventory.models.Article;
import com.myorg.inventory.models.ArticleRelationship;
import com.myorg.inventory.repositories.ArticleRelationshipRepository;
import com.myorg.inventory.repositories.ArticleRepository;
import com.myorg.inventory.util.ApplicationProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@ExtendWith(MockitoExtension.class)
public class ArticleRelationShipServiceTests {

    @InjectMocks
    private ArticleRelationshipService articleRelationshipService;

    @Mock
    private ArticleRelationshipRepository articleRelationshipRepository;

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    ApplicationProperties applicationProperties;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Mock
    HashMap<String, String> hashMap;


    @Captor
    ArgumentCaptor<String> repoCapture;
    @Captor
    ArgumentCaptor<String> methodCaptor;

    private List<ProductBean> productBeanList;
    private Products products;
    private ArticleRelationship articleRelationship;
    private Article articleProduct, articleChild;

    @BeforeEach
    void setUp() {
        this.productBeanList = new ArrayList<>();
        List<ArticleRelationship> relationshipList = new ArrayList<>();
        List<ArticleBean> articleBeanList = new ArrayList<>();
        articleRelationship = new ArticleRelationship(1,2,articleChild,articleProduct);
        relationshipList.add(articleRelationship);
        ArticleBean articleBean = new ArticleBean("1","Item","Table",16, 280L,true, "1", relationshipList);
        articleBean.setAmount_of(2);
        articleBeanList.add(articleBean);

        ProductBean productOne = new ProductBean("11","Table",true, articleBeanList);
        //ProductBean productTwo = new ProductBean("12", "Chair", true, articleBeanList);

        productBeanList.add(productOne);
        //productBeanList.add(productTwo);

        this.products = new Products();
        products.setProducts(productBeanList);

        articleChild = new Article("1", "Item", "Screw",
                100, Long.valueOf(200), false, relationshipList);
        articleChild.setArtId(1);
        articleProduct = new Article("11","Product", "Table",0,Long.valueOf(0),true, relationshipList);
        articleProduct.setArtId(5);
    }

    @Test
    public void saveTest()
    {
        Mockito.when(articleRepository.findByArtNumber("11")).thenReturn(articleProduct);
        Mockito.when(articleRepository.findByArtNumber("1")).thenReturn(articleChild);
        Mockito.when(articleRepository.saveAndFlush(articleProduct)).thenReturn(articleProduct);
        Mockito.when(articleRelationshipRepository.findByChildArticle_ArtIdAndParentArticle_ArtId(articleChild.getArtId(),articleProduct.getArtId())).thenReturn(articleRelationship);
        Mockito.when(articleRepository.getOne(1)).thenReturn(articleChild);
        Mockito.when(articleRepository.getOne(5)).thenReturn(articleProduct);

        articleRelationshipService.saveArticleRelationship(products.getProducts());

    }
}
