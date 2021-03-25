package com.myorg.inventory.repositories;

import com.myorg.inventory.models.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer>, ArticleRepositoryCustom {
    @Override
    @Query("select articles from article articles where articles.sellable=true")
    //@Query("select articles from article articles left join fetch articles.listArticleRelationships relationships left join fetch relationships.childArticle")
    List<Article> findAll();

    Article findByArtNumber(String artNumber);

}
