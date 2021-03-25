package com.myorg.inventory.repositories;

import com.myorg.inventory.models.Article;
import com.myorg.inventory.models.ArticleDBView;
import com.myorg.inventory.repositories.ArticleRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ArticleDBViewRepository extends JpaRepository<ArticleDBView, Integer> {
    @Override
    @Query("select articles from articles_view articles left join fetch articles.article")
    List<ArticleDBView> findAll();

    Article findByArtNumber(String artNumber);

}
