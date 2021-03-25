package com.myorg.inventory.services;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.myorg.inventory.repositories.ArticleRelationshipRepository;
import com.myorg.inventory.repositories.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DependencyInjectorFactory {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleRelationshipService articleRelationshipService;

    public DependencyInjectorFactory() {

    }

    @PostConstruct
    public void initEventFactory() {
        DependencyInjector.setArticleService(articleService);
        DependencyInjector.setArticleRelationshipService(articleRelationshipService);

    }



}
