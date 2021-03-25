package com.myorg.inventory.repositories;

import com.myorg.inventory.models.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepositoryCustom {

    public List<Article> fetchProducts();

}
