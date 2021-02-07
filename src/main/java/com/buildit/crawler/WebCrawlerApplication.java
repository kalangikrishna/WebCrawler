package com.buildit.crawler;

import com.buildit.crawler.service.WebCrawlerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@Configuration
@ComponentScan
public class WebCrawlerApplication /*implements CommandLineRunner*/ {

    @Autowired
    private WebCrawlerService webCrawlerService;

    public static void main(String[] args) throws IOException {
        log.info("Starting  web crawler for");
        WebCrawlerApplication application = new WebCrawlerApplication();
        application.getWebCrawlerService().beginWebsiteCrawlForDomain(args[0]);
//        SpringApplication.run(WebCrawlerApplication.class, args);
    }

//    @Override
//    public void run(String... args) throws Exception {
//        log.info(args[0]);

//    }


    public WebCrawlerService getWebCrawlerService() {
        return webCrawlerService;
    }
}
