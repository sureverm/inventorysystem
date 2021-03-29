package com.myorg.inventory.controlles.integration.productrange;

import com.myorg.inventory.IntegrationTest.TestInventoryAppIT;
import com.myorg.inventory.controllers.integration.productrange.ProductRangeAppIntegration;
import com.myorg.inventory.controllers.integration.productrange.beans.ArticleBean;
import com.myorg.inventory.controllers.integration.productrange.beans.ProductBean;
import com.myorg.inventory.controllers.integration.productrange.beans.Products;
import com.myorg.inventory.models.Article;
import com.myorg.inventory.models.ArticleRelationship;
import com.myorg.inventory.services.ArticleRelationshipService;
import com.myorg.inventory.services.ArticleService;
import com.myorg.inventory.util.ApplicationProperties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@WebMvcTest(controllers = TestInventoryAppIT.class)
public class ProductRangeAppIntegrationTest {

    @MockBean
    private ArticleService articleService;
    @MockBean
    private ArticleRelationshipService articleRelationshipService;

    @MockBean
    private ProductRangeAppIntegration appIntegration;

    @Mock
    ApplicationProperties applicationProperties;

    @Autowired
    private MockMvc mockMvc;

    private List<Article> articleList;
    private List<ProductBean> productBeanList;
    private Products products;
    private ArticleRelationship articleRelationship;

    @BeforeEach
    void setUp() {
        this.productBeanList = new ArrayList<>();
        List<ArticleRelationship> relationshipList = new ArrayList<>();
        this.articleList = new ArrayList<>();

        Article articleOne = new Article("11", "Item", "Test Item To Save",
                100, Long.valueOf(200), false, relationshipList);
        Article articleTwo = new Article("22", "Item", "Test Item To Save",
                100, Long.valueOf(200), false, relationshipList);
        Article articleThree = new Article("33", "Item", "Test Item To Save",
                100, Long.valueOf(200), false, relationshipList);

        articleList.add(articleOne);
        articleList.add(articleTwo);
        articleList.add(articleThree);


        ProductBean productOne = new ProductBean("11","Table",true, null);
        ProductBean productTwo = new ProductBean("12", "Chair", true, null);

        productBeanList.add(productOne);
        productBeanList.add(productTwo);

        List<ArticleBean> articleBeanList = new ArrayList<>();
        ArticleBean articleBean = new ArticleBean("","Item","Table",16, 280L,true, relationshipList);
        articleBeanList.add(articleBean);
        this.products = new Products();
        products.setProducts(productBeanList);
    }

    @Test
    public void fetchArticles() throws Exception {

        Mockito.when(articleService.save(null)).thenReturn(articleList);
        appIntegration.loadInventoryArticles();

        Assertions.assertEquals(3,articleList.size());
    }

    @Test
    public void fetchProducts() throws URISyntaxException
    {
        //Mockito.mock(articleRelationshipService.saveArticleRelationship(products.getProducts()));
        appIntegration.loadInventoryArticles();

        Assertions.assertEquals(2,productBeanList.size());
    }
}
