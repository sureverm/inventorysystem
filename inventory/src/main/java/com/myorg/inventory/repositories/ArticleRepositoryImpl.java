package com.myorg.inventory.repositories;

import com.myorg.inventory.models.Article;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class ArticleRepositoryImpl implements ArticleRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Article> fetchProducts() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Article> q = cb.createQuery(Article.class);
        /*Root<Country> c = q.from(Country.class);
        q.multiselect(c.get("currency"), cb.sum(c.get("population")));
        q.where(cb.isMember("Europe", c.get("continents")));
        q.groupBy(c.get("currency"));
        g.having(cb.gt(cb.count(c), 1));*/

        return new ArrayList<Article>();
    }
}
