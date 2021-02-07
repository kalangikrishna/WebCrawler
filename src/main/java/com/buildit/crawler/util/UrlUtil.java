package com.buildit.crawler.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Set;

public class UrlUtil {

    public static boolean isValidUrlForDomain(String url, String domainName) {
        try {
            URI uri = new URI(url);
            String domain = uri.getHost();
            return domain != null && domain.contains(domainName);
        } catch (URISyntaxException e) {
            // invalid url
            return false;
        }
    }

    public static boolean isUrlEligibleToTraverse(String url, Set<String> traversedList) {
        boolean isEligible = !url.matches(".*/#.*$");
        int queryParamBeginIndex = url.indexOf("/?");
        if (queryParamBeginIndex > 0) {
            isEligible = isEligible & !traversedList.contains(url.substring(0, queryParamBeginIndex));
        }
        return isEligible;
    }

}
