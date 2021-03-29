package com.myorg.inventory.controllers.integration.productrange;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myorg.inventory.controllers.integration.productrange.beans.Articles;
import com.myorg.inventory.controllers.integration.productrange.beans.Products;
import com.myorg.inventory.services.ArticleRelationshipService;
import com.myorg.inventory.services.ArticleService;
import com.myorg.inventory.util.ApplicationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;

@Component
@EnableScheduling
public class ProductRangeAppIntegration {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductRangeAppIntegration.class);

    @Autowired
    ApplicationProperties applicationProperties;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleRelationshipService articleRelationshipService;

    /*
    * Placeholder for implementing timeout when integrating with other systems.
    */
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .setConnectTimeout(Duration.ofMillis(120000))
                .setReadTimeout(Duration.ofMillis(120000))
                .build();
    }

    @Autowired
    private RestTemplate restTemplate;

    protected HttpEntity initHeaders(){

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        return new HttpEntity<>(headers);

    }

    /*
     * Loads inventory articles form a dummy rest api implemented using either a standalone json-server setup or
     * json-server provided by github http://my-json-server.typicode.com
     */
    @Scheduled(cron="${integ.productrangeapp.inventory.job}")
    public void loadInventoryArticles(){
        LOGGER.debug("Inside loadInventoryArticles!!!!!!!!");

        ResponseEntity<Articles> response = restTemplate.exchange(applicationProperties.getProductRangeAppInventoryUrl(), HttpMethod.GET, initHeaders(), Articles.class);

        LOGGER.debug("Received Response from ProductRangeApp for Articles list");

        if(null!= response.getBody())
            articleService.save(response.getBody().getInventory());

        loadInventoryProducts();
    }

    /*
     * Loads inventory products form a dummy rest api implemented using either a standalone json-server setup or
     * json-server provided by github http://my-json-server.typicode.com
     */
    @Scheduled(cron="${integ.productrangeapp.products.job}")
    public void loadInventoryProducts(){
        LOGGER.debug("Inside loadInventoryProducts!!!!!!!!");

        ResponseEntity<Products> response = restTemplate.exchange(applicationProperties.getProductRangeAppProductsUrl(), HttpMethod.GET, initHeaders(), Products.class);

        LOGGER.debug("Received Response from ProductRangeApp for Products list");

        if(null!= response.getBody())
            articleRelationshipService.saveArticleRelationship(response.getBody().getProducts());
    }


    /**
     * Reads articles information form a file location. Placeholder created.
     * @throws IOException
     */
    public void loadInventoryArticlesFromFile() throws IOException {
        LOGGER.debug("Inside loadInventoryArticlesFromFile!!!!!!!!");

        ObjectMapper mapper = new ObjectMapper();
        Articles articles = mapper.readValue(new File(""), Articles.class);

        LOGGER.debug("Received Response from ProductRangeApp for Articles list");

        if(null!= articles)
            articleService.save(articles.getInventory());

        loadInventoryProductsFromFile();
    }

    /**
     * Reads products information form a file location. Placeholder created.
     * @throws IOException
     */
    public void loadInventoryProductsFromFile() throws IOException {
        LOGGER.debug("Inside loadInventoryProductsFromFile!!!!!!!!");

        ObjectMapper mapper = new ObjectMapper();
        Products products = mapper.readValue(new File(""), Products.class);

        LOGGER.debug("Received Response from ProductRangeApp for Products list");

        if(null!= products)
            articleRelationshipService.saveArticleRelationship(products.getProducts());
    }
}
