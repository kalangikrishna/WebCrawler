package com.buildit.crawler.service;


import org.junit.Test;

import java.io.IOException;

public class WebCrawlerServiceTest {

    @Test
    public void testGetDocument() throws IOException {
        WebCrawlerService crawlerService = new WebCrawlerService();
        crawlerService.getDocument("https://wiprodigital.com");
    }
}