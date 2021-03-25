package com.myorg.inventory.services;

import com.myorg.inventory.repositories.ArticleRelationshipRepository;
import com.myorg.inventory.repositories.ArticleRepository;
import org.springframework.stereotype.Component;

@Component
public class DependencyInjector {

    private static ArticleService articleService;

    private static ArticleRelationshipService articleRelationshipService;

    public static void setArticleService(ArticleService articleService) {

    }

    public static ArticleService getArticleService() {
        return articleService;
    }

    public static void setArticleRelationshipService(ArticleRelationshipService articleRelationshipService) {

    }

    public static ArticleRelationshipService getArticleRelationshipService() {
        return articleRelationshipService;
    }
}
