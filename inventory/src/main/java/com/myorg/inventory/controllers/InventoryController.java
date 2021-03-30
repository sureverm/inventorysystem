package com.myorg.inventory.controllers;

import com.myorg.inventory.controllers.beans.SoldProductsOrderDetails;
import com.myorg.inventory.controllers.integration.productrange.beans.Articles;
import com.myorg.inventory.controllers.integration.productrange.beans.InventoryResponse;
import com.myorg.inventory.models.Article;
import com.myorg.inventory.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1")
public class InventoryController {

    @Autowired
    private ArticleService articleService;

    /**
     * This method returns a list of sellable Products from inventory DB.
     * @param qparams any query param for filtering the Products from inventory DB. An option provided here but not implemented in the service layer class.
     * @return list of Products which are sellable. For now only sellable Products are returned. Response is in json format.
     */
    @GetMapping
    @RequestMapping("/products")
    public List<Article> buyableProducts(@RequestParam(required=false)  Map<String,String> qparams) {
        return articleService.listAll(qparams);
    }

    /**
     * A REST API method which can be invoked to update the stock status based on incoming request when Products are sold be Selling service.
     * It is assumed that only one Order detail's payload will be sent to this method.
     * @param soldProductsOrderDetails the incoming json payload for updating the Product's stock status when Products are sold.
     * @param bindingResult an object to return any errors (null or zero values) in the response.
     * @return
     */
    @PostMapping
    @RequestMapping("/products/updateStock")
    public ResponseEntity<InventoryResponse> updateArticleStock(@Valid @RequestBody SoldProductsOrderDetails soldProductsOrderDetails,
                                                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InputMismatchException(bindingResult.toString());

        }
        InventoryResponse responseObj =  articleService.updateArticleStock(soldProductsOrderDetails);
        return ResponseEntity.ok(responseObj);
    }

    /**
     *
     * @param art_id the Article number or Product number for which details are to be fetched
     * @return Article or Product matching the incoming parameter.
     */
    @GetMapping
    @RequestMapping("</products/{art_id>}")
    public Article getArticle(@PathVariable String art_id){
        return articleService.getArticle(art_id);
    }

}
