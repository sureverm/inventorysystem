package com.myorg.inventory.controlles;

import com.myorg.inventory.controllers.InventoryController;
import com.myorg.inventory.models.Article;
import com.myorg.inventory.services.ArticleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@WebMvcTest(controllers = InventoryController.class)
@ActiveProfiles("test")
public class InventoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArticleService articleService;

    private List<Article> articleList;

    @BeforeEach
    void setUp() {
        this.articleList = new ArrayList<>();
        Article articleOne = new Article("11", "Item", "Test Item To Save",
                100, Long.valueOf(200), false, null);
        Article articleTwo = new Article("22", "Item", "Test Item To Save",
                100, Long.valueOf(200), false, null);
        Article articleThree = new Article("33", "Item", "Test Item To Save",
                100, Long.valueOf(200), false, null);

        articleList.add(articleOne);
        articleList.add(articleTwo);
        articleList.add(articleThree);
    }

    @Test
    public void listProducts() throws Exception{
        Mockito.when(articleService.listAll(new HashMap<>())).thenReturn(articleList);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/api/v1/products").accept(
                MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        System.out.println(result.getResponse());
        String expected = "[{artId:null,artNumber:11,artType:Item,name:Test Item To Save,price:200,sellable:false,listArticleRelationships:null},{artId:null,artNumber:22,artType:Item,name:Test Item To Save,price:200,sellable:false,listArticleRelationships:null},{artId:null,artNumber:33,artType:Item,name:Test Item To Save,price:200,sellable:false,listArticleRelationships:null}]";

        Assertions.assertEquals(expected, result.getResponse().getContentAsString().replaceAll("\"",""));
    }

}
