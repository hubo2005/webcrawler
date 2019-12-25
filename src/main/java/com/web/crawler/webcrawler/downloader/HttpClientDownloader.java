package com.web.crawler.webcrawler.downloader;

import com.web.crawler.webcrawler.model.Page;
import com.web.crawler.webcrawler.model.Request;
import com.web.crawler.webcrawler.model.Site;
import com.web.crawler.webcrawler.utils.HttpClientGenerator;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class HttpClientDownloader implements Downloader{


    private final Map<String, CloseableHttpClient> httpClients = new HashMap<String, CloseableHttpClient>();
    private HttpClientGenerator httpClientGenerator = new HttpClientGenerator();

    @Override
    public Page download(Request request) {
        CloseableHttpResponse httpResponse = null;
        CloseableHttpClient httpClient = getHttpClient();
       // Proxy proxy = proxyProvider != null ? proxyProvider.getProxy(task) : null;
        System.out.println("downloading +++++" + request.getUrl());
        return null;
    }

    private CloseableHttpClient getHttpClient() {
        return getHttpClient(HttpConfiguration.getInstance());
    }

    private CloseableHttpClient getHttpClient(HttpConfiguration site) {
        if (site == null) {
            return httpClientGenerator.getClient(HttpConfiguration.getInstance());
        }
        String domain = site.getDomain();
        CloseableHttpClient httpClient = httpClients.get(domain);
        if (httpClient == null) {
            synchronized (this) {
                httpClient = httpClients.get(domain);
                if (httpClient == null) {
                    httpClient = httpClientGenerator.getClient(site);
                    httpClients.put(domain, httpClient);
                }
            }
        }
        return httpClient;
    }
}
