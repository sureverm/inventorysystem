package com.myorg.inventory.controllers.integration.productrange;

import com.myorg.inventory.controllers.integration.productrange.beans.Articles;
import com.myorg.inventory.controllers.integration.productrange.beans.Products;
import com.myorg.inventory.services.ArticleRelationshipService;
import com.myorg.inventory.services.ArticleService;
import com.myorg.inventory.services.DependencyInjector;
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
import org.springframework.web.client.RestTemplate;

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

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .setConnectTimeout(Duration.ofMillis(120000))
                .setReadTimeout(Duration.ofMillis(120000))
                .build();
    }

    @Autowired
    private RestTemplate restTemplate;

    @Scheduled(cron="${integ.productrangeapp.inventory.job}")
    public void loadInventoryArticles(){
        LOGGER.debug("Inside loadInventoryArticles!!!!!!!!");

        ResponseEntity<Articles> response = restTemplate.exchange(applicationProperties.getProductRangeAppInventoryUrl(), HttpMethod.GET, initHeaders(), Articles.class);

        LOGGER.debug("Received Response from ProductRangeApp for Articles list");

        if(null!= response.getBody())
            articleService.save(response.getBody().getInventory());

        loadInventoryProducts();
    }


    @Scheduled(cron="${integ.productrangeapp.products.job}")
    public void loadInventoryProducts(){
        LOGGER.debug("Inside loadInventoryProducts!!!!!!!!");

        ResponseEntity<Products> response = restTemplate.exchange(applicationProperties.getProductRangeAppProductsUrl(), HttpMethod.GET, initHeaders(), Products.class);

        LOGGER.debug("Received Response from ProductRangeApp for Products list");

        if(null!= response.getBody())
            articleRelationshipService.saveArticleRelationship(response.getBody().getProducts());
    }

    protected HttpEntity initHeaders(){

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        return new HttpEntity<>(headers);

    }

}
