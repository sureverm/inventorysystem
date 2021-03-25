package com.myorg.inventory.IntegrationTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class TestInventoryAppIT {

    private MockMvc mockMvc;

    @Test
    public void fetchArticles() throws Exception {
        mockMvc.perform(get("http://localhost:3000/inventory"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andReturn();

    }

    @Test
    public void testGetEmployeeListSuccess() throws URISyntaxException
    {
        RestTemplate restTemplate = new RestTemplate();

        final String baseUrl = "http://localhost:3000/inventory";
        URI uri = new URI(baseUrl);

        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);

        //Verify request succeed
        Assertions.assertEquals(200, result.getStatusCodeValue());
        Assertions.assertEquals(true, result.getBody().contains("inventory"));
    }
}
