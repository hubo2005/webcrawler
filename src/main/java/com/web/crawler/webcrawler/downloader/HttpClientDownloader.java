package com.web.crawler.webcrawler.downloader;

import com.web.crawler.webcrawler.constant.CrawlerConstants;
import com.web.crawler.webcrawler.model.Page;
import com.web.crawler.webcrawler.model.Request;
import com.web.crawler.webcrawler.spider.ParsePageJob;
import com.web.crawler.webcrawler.spider.SpiderJob;
import com.web.crawler.webcrawler.utils.HttpClientGenerator;
import com.web.crawler.webcrawler.utils.WebcrawlerUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

@Component
public class HttpClientDownloader implements Downloader{

    private Logger logger = LoggerFactory.getLogger(getClass());

    private final Map<String, CloseableHttpClient> httpClients = new HashMap<String, CloseableHttpClient>();
    private HttpClientGenerator httpClientGenerator = new HttpClientGenerator();
    private HttpUriRequestConverter httpUriRequestConverter = new HttpUriRequestConverter();


    @Override
    public Page download(Request request) {
        CloseableHttpResponse httpResponse = null;
        CloseableHttpClient httpClient = getHttpClient(request);
       // Proxy proxy = proxyProvider != null ? proxyProvider.getProxy(task) : null;
        HttpClientRequestContext requestContext = httpUriRequestConverter.convert(request);
        try {
            httpResponse = httpClient.execute(requestContext.getHttpUriRequest(),requestContext.getHttpClientContext());
            logger.info("downloading page sucesss {}", request.getUrl());
            Page page = handleResponse(request,request.getCharset(),httpResponse);
            return page;
        }catch (IOException e) {
            logger.warn("download page {} error", request.getUrl(), e);
        }finally {
            if (httpResponse != null) {
                //ensure the connection is released back to pool
                EntityUtils.consumeQuietly(httpResponse.getEntity());
            }
//            if (proxyProvider != null && proxy != null) {
//                proxyProvider.returnProxy(proxy, page, task);
//            }
        }
        return null;
    }

    private CloseableHttpClient getHttpClient(Request request) {
        HttpConfiguration httpConfiguration = getHttpConfigurationByRequest(request);
        String domain = request.getDomain();
        if (StringUtils.isEmpty(domain)) {
            domain = "default";
        }

        CloseableHttpClient httpClient = httpClients.get(domain);
        if (httpClient == null) {
            synchronized (this) {
                httpClient = httpClients.get(domain);
                if (httpClient == null) {
                    httpClient = httpClientGenerator.getClient(httpConfiguration);
                    httpClients.put(domain, httpClient);
                }
            }
        }
        return httpClient;
    }


    private HttpConfiguration getHttpConfigurationByRequest(Request request) {
        return HttpConfiguration.getDefaultConfiguration();
    }

    protected Page handleResponse(Request request, String charset, HttpResponse httpResponse) throws IOException {
        byte[] bytes = IOUtils.toByteArray(httpResponse.getEntity().getContent());
        String contentType = httpResponse.getEntity().getContentType() == null ? "" : httpResponse.getEntity().getContentType().getValue();
        Page page = new Page();
        page.setBytes(bytes);
        if (!request.isBinaryContent()){
            if (charset == null) {
                charset = getHtmlCharset(contentType, bytes);
            }
            page.setCharset(charset);
            page.setRawText(new String(bytes, charset));
        }
        page.setUrl(request.getUrl());
        page.setRequest(request);
        page.setStatusCode(httpResponse.getStatusLine().getStatusCode());
        page.setDownloadSuccess(true);
//        if (responseHeader) {
//            page.setHeaders(WebcrawlerUtils.convertHeaders(httpResponse.getAllHeaders()));
//        }
        return page;
    }

    private String getHtmlCharset(String contentType, byte[] contentBytes) throws IOException {
        String charset = WebcrawlerUtils.detectCharset(contentType, contentBytes);
        if (charset == null) {
            charset = Charset.defaultCharset().name();
            logger.warn("Charset autodetect failed, use {} as charset. Please specify charset in Site.setCharset()", Charset.defaultCharset());
        }
        return charset;
    }

}
