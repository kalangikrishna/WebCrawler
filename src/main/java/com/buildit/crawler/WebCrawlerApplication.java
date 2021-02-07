package com.buildit.crawler;

import com.buildit.crawler.service.WebCrawlerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@ComponentScan
public class WebCrawlerApplication implements CommandLineRunner {

    @Autowired
    private WebCrawlerService webCrawlerService;

    public static void main(String[] args) {
        log.info("Starting  web crawler for");
        SpringApplication.run(WebCrawlerApplication.class, args);
    }

    @Override
    public void run(String... args) {
        webCrawlerService.beginWebsiteCrawlForDomain("https://wiprodigital.com");
    }
}
