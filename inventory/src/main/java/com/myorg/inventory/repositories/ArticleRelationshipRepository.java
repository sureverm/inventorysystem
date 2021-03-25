package com.myorg.inventory.repositories;

import com.myorg.inventory.models.Article;
import com.myorg.inventory.models.ArticleRelationship;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRelationshipRepository extends JpaRepository<ArticleRelationship, Integer> {

    ArticleRelationship findByChildArticle_ArtIdAndParentArticle_ArtId(Integer childArtId, Integer parentArtId);
}
