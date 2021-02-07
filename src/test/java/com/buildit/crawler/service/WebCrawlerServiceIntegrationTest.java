package com.buildit.crawler.service;


import com.buildit.crawler.WebCrawlerApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebCrawlerApplication.class)
public class WebCrawlerServiceIntegrationTest {

    @Autowired
    private WebCrawlerApplication webCrawlerApplication;

    @Test
    public void testGetDocument() {
        webCrawlerApplication.run("https://wiprodigital.com");
    }
}