package com.myorg.inventory.IntegrationTest;

import com.myorg.inventory.controllers.InventoryController;
import com.myorg.inventory.controllers.integration.productrange.beans.ArticleBean;
import com.myorg.inventory.controllers.integration.productrange.beans.Articles;
import com.myorg.inventory.controllers.integration.productrange.beans.Products;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = TestInventoryAppIT.class)
public class TestInventoryAppIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void fetchArticles() throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        final String baseUrl = "http://localhost:3000/inventory";
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

        final String baseUrl = "http://localhost:3000/products";
        URI uri = new URI(baseUrl);

        ResponseEntity<Products> result = restTemplate.getForEntity(uri, Products.class);

        //Verify request succeed
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(false, CollectionUtils.isEmpty(result.getBody().getProducts()));
    }
}
