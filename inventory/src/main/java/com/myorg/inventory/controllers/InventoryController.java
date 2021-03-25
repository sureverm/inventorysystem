package com.myorg.inventory.controllers;

import com.myorg.inventory.controllers.integration.productrange.beans.Articles;
import com.myorg.inventory.models.Article;
import com.myorg.inventory.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1")
public class InventoryController {

    @Autowired
    private ArticleService articleService;

    @GetMapping
    @RequestMapping("/products")

    public List<Article> buyableProducts(@RequestParam(required=false)  Map<String,String> qparams) {
        return articleService.listAll(qparams);
    }

    @PutMapping
    @RequestMapping("/products/updateStock")
    public String updateArticleStock(@RequestBody Articles articles) {
        return articleService.updateArticleStock(articles);
    }

    @GetMapping
    @RequestMapping("</products/{art_id>}")
    public Article getArticle(@PathVariable String art_id){
        return articleService.getArticle(art_id);
    }

}
