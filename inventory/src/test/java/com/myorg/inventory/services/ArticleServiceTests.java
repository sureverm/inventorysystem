package com.myorg.inventory.services;

import com.myorg.inventory.controllers.beans.SoldProducts;
import com.myorg.inventory.controllers.beans.SoldProductsOrderDetails;
import com.myorg.inventory.controllers.integration.productrange.beans.ArticleBean;
import com.myorg.inventory.controllers.integration.productrange.beans.Articles;
import com.myorg.inventory.controllers.integration.productrange.beans.InventoryResponse;
import com.myorg.inventory.models.Article;
import com.myorg.inventory.models.ArticleRelationship;
import com.myorg.inventory.repositories.ArticleRepository;
import com.myorg.inventory.util.ApplicationProperties;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@ExtendWith(MockitoExtension.class)
public class ArticleServiceTests {

    @InjectMocks
    private ArticleService articleService;

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

    private List<Article> articleList;
    private SoldProductsOrderDetails soldProductsOrderDetails;
    private Articles articles;

    @BeforeEach
    void setUp() {
        this.articleList = new ArrayList<>();
        List<ArticleRelationship> relationshipList = new ArrayList<>();

        Article articleOne = new Article("11", "Item", "Test Item To Save",
                100, Long.valueOf(200), false, relationshipList);
        Article articleTwo = new Article("22", "Item", "Test Item To Save",
                100, Long.valueOf(200), false, relationshipList);
        Article articleThree = new Article("33", "Item", "Test Item To Save",
                100, Long.valueOf(200), false, relationshipList);

        articleList.add(articleOne);
        articleList.add(articleTwo);
        articleList.add(articleThree);


        List<SoldProducts> soldProductsList = new ArrayList<>();
        SoldProducts soldProduct = new SoldProducts(1,"11");
        soldProductsList.add(soldProduct);
        this.soldProductsOrderDetails = new SoldProductsOrderDetails();
        soldProductsOrderDetails.setSoldProduclsList(soldProductsList);

        List<ArticleBean> articleBeanList = new ArrayList<>();
        ArticleBean articleBean = new ArticleBean("11","Item","Table",16, 280L,true, relationshipList);
        articleBeanList.add(articleBean);
        this.articles = new Articles();
        articles.setInventory(articleBeanList);
    }

    @Test
    public void listAll()
    {
        Mockito.when(articleRepository.findAll()).thenReturn(articleList);
        //test
        List<Article> empList = articleService.listAll(new HashMap<>());

        Assertions.assertEquals(3, empList.size());
        Mockito.verify(articleRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void updateArticleStockTest()
    {
        Mockito.when(articleRepository.findByArtNumber("11")).thenReturn(articleList.get(0));

        InventoryResponse result = articleService.updateArticleStock(soldProductsOrderDetails);

        Assertions.assertEquals(true, result.getStatusMessage().toLowerCase().contains("success"));

    }

    @Test
    public void saveTest()
    {
        Mockito.when(articleRepository.findByArtNumber("11")).thenReturn(articleList.get(0));

        List<Article> articleSaved = articleService.save(articles.getInventory());

        Assertions.assertNotNull(articleSaved, "The saved articleSaved should not be null");
        Assertions.assertEquals(0, articleSaved.size(), "The articleSaved size should be 0");
    }
}
