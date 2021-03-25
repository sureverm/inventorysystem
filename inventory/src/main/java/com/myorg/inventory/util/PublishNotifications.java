package com.myorg.inventory.util;

import com.myorg.inventory.services.ArticleService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class PublishNotifications {

    private static final Logger logger = LogManager.getLogger(ArticleService.class);

    public static void sendNotificationToDownstreams(String downstream, String message){
        /*
        * TODO send notification to ApplicationSystems to handle the given situation
        * */
    }
}
