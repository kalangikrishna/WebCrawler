package com.buildit.crawler.service;

import com.buildit.crawler.exception.ClientException;
import com.buildit.crawler.exception.DomParseException;
import com.buildit.crawler.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.IntStream;

import static com.buildit.crawler.util.UrlUtil.isUrlEligibleToTraverse;
import static com.buildit.crawler.util.UrlUtil.isValidUrlForDomain;

@Service
@Slf4j
public class WebCrawlerService {

    private ClientBuilder clientBuilder;
    private ParserService parserService;
    private String domainName;
    private String crawlResult = "";

    WebCrawlerService(ClientBuilder clientBuilder, ParserService parserService) {
        this.clientBuilder = clientBuilder;
        this.parserService = parserService;
    }

    public void beginWebsiteCrawlForDomain(String baseUrl) {
        Request request = new Request.Builder()
                .url(baseUrl)
                .build();
        domainName = request.url().topPrivateDomain();
        try {
            crawlWebsite(clientBuilder.getSslEnabledHttpclient(), request, baseUrl, new HashSet<>(), 0);
            if (crawlResult.length() > 0) {
                try (FileWriter fileWriter = new FileWriter("crawlResult.txt")) {
                    PrintWriter printWriter = new PrintWriter(fileWriter);
                    printWriter.print(crawlResult);
                } catch (IOException ex) {
                    log.error("Error writing crawl result to file");
                }
            }
        } catch (ClientException e) {
            log.error("Error retrieving client");
            throw new ServiceException("Error retrieving client", e);
        }
    }


    private void crawlWebsite(OkHttpClient client, Request request, String url, Set<String> traversedList, int traverseDepth) {
        if (url.endsWith("/")) {
            url = url.substring(0,url.length()-1);
        }
        if (!traversedList.contains(url)) {
            Document document;
            try (Response response = client.newCall(request).execute()) {
                ResponseBody responseBody = response.body();
                document = parserService.getDocumentFromString(responseBody.string());
            } catch (IOException | DomParseException e) {
                log.error("exception traversing url " + url + " at depth " + traverseDepth);
                throw new ServiceException("exception traversing url " + url + " at depth " + traverseDepth, e);
            }

            String depthRepeater = String.join("", Collections.nCopies(traverseDepth, "\t"));
            log.info(depthRepeater + url);
            crawlResult = crawlResult.concat(depthRepeater + url + "\n");
            traversedList.add(url);

            NodeList anchorTags = document.getElementsByTagName("a");
            int newTraverseDepth = ++traverseDepth;

            IntStream.range(0, anchorTags.getLength()).forEach(idx -> {
                Node node = anchorTags.item(idx).getAttributes().getNamedItem("href");
                if (node != null) {
                    String nextUrl = node.getNodeValue();
                    if (isValidUrlForDomain(nextUrl, domainName) && isUrlEligibleToTraverse(nextUrl, traversedList)) {
                        crawlWebsite(client,
                                new Request.Builder().url(nextUrl).build(),
                                nextUrl,
                                traversedList, newTraverseDepth);
                    }
                }
            });
        }
    }
}
