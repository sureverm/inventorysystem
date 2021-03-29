package com.myorg.inventory.IntegrationTest;

import com.myorg.inventory.controllers.integration.productrange.beans.Articles;
import com.myorg.inventory.controllers.integration.productrange.beans.Products;
import com.myorg.inventory.services.ArticleService;
import com.myorg.inventory.util.ApplicationProperties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@WebMvcTest(controllers = TestInventoryAppIT.class)
public class TestInventoryAppIT {

    @MockBean
    private ArticleService articleService;

    @Mock
    ApplicationProperties applicationProperties;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void fetchArticles() throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        final String baseUrl = "http://my-json-server.typicode.com/sureverm/inventorysystem/inventory"; //applicationProperties.getProductRangeAppInventoryUrl();
        URI uri = new URI(baseUrl);

        ResponseEntity<Articles> result = restTemplate.getForEntity(uri, Articles.class);

        //Verify request succeed
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(false, CollectionUtils.isEmpty(result.getBody().getInventory()));

    }

    @Test
    public void fetchProducts() throws URISyntaxException
    {
        RestTemplate restTemplate = new RestTemplate();

        final String baseUrl = "http://my-json-server.typicode.com/sureverm/inventorysystem/products"; //applicationProperties.getProductRangeAppProductsUrl();
        URI uri = new URI(baseUrl);

        ResponseEntity<Products> result = restTemplate.getForEntity(uri, Products.class);

        //Verify request succeed
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(false, CollectionUtils.isEmpty(result.getBody().getProducts()));
    }
}
