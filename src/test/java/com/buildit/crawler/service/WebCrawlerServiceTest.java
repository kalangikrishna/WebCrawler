package com.buildit.crawler.service;

import com.buildit.crawler.exception.ClientException;
import com.buildit.crawler.exception.DomParseException;
import com.buildit.crawler.exception.ServiceException;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.w3c.dom.Document;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class WebCrawlerServiceTest {

    @Mock
    private ClientBuilder mockClientBuilder;

    @Mock
    private ParserService mockParserService;

    @Mock
    private OkHttpClient mockHttpClient;

    @Mock
    private Call mockCall;

    @Mock
    private Document mockDocument;

    @InjectMocks
    private WebCrawlerService webCrawlerService;

    @Test(expected = ServiceException.class)
    public void testBeginWebsiteCrawlForDomain_clientError() throws ClientException, DomParseException {
        when(mockClientBuilder.getSslEnabledHttpclient()).thenThrow(new ClientException("error preparing client"));
        try {
            webCrawlerService.beginWebsiteCrawlForDomain("https://www.google.com");
        } catch (ServiceException ex) {
            verify(mockClientBuilder).getSslEnabledHttpclient();
            verify(mockParserService,never()).getDocumentFromString(anyString());
            throw ex;
        }
    }

    @Test(expected = ServiceException.class)
    public void testBeginWebsiteCrawlForDomain_clientCallError() throws ClientException, IOException, DomParseException {
        when(mockClientBuilder.getSslEnabledHttpclient()).thenReturn(mockHttpClient);
        when(mockHttpClient.newCall(any(Request.class))).thenReturn(mockCall);
        when(mockCall.execute()).thenThrow(new IOException("IO error trying to make client call"));

        try {
            webCrawlerService.beginWebsiteCrawlForDomain("https://www.google.com");
        } catch (ServiceException ex) {
            verify(mockClientBuilder).getSslEnabledHttpclient();
            verify(mockParserService,never()).getDocumentFromString(anyString());
            throw ex;
        }
    }

}
