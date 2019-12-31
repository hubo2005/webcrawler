package com.web.crawler.webcrawler.downloader;

import com.web.crawler.webcrawler.model.Request;
import com.web.crawler.webcrawler.model.Site;
import com.web.crawler.webcrawler.utils.HttpConstant;
import com.web.crawler.webcrawler.utils.WebcrawlerUtils;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;

import java.util.Map;

public class HttpUriRequestConverter {

    public HttpClientRequestContext convert(Request request) {
        HttpClientRequestContext httpClientRequestContext = new HttpClientRequestContext();
        httpClientRequestContext.setHttpUriRequest(convertHttpUriRequest(request));
        httpClientRequestContext.setHttpClientContext(convertHttpClientContext(request));
        return httpClientRequestContext;
    }


    private HttpUriRequest convertHttpUriRequest(Request request) {
        RequestBuilder requestBuilder = selectRequestMethod(request).setUri(WebcrawlerUtils.fixIllegalCharacterInUrl(request.getUrl()));
        Site site = request.getSiteInfo();
        if (site!=null && null!= site.getHeaders()) {
            for (Map.Entry<String, String> headerEntry : site.getHeaders().entrySet()) {
                requestBuilder.addHeader(headerEntry.getKey(), headerEntry.getValue());
            }
        }

        RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();
        if (null!=site) {
            requestConfigBuilder.setConnectionRequestTimeout(site.getTimeOut())
                    .setSocketTimeout(site.getTimeOut())
                    .setConnectTimeout(site.getTimeOut())
                    .setCookieSpec(CookieSpecs.STANDARD);
        }

//        if (proxy != null) {
//            requestConfigBuilder.setProxy(new HttpHost(proxy.getHost(), proxy.getPort()));
//        }
        requestBuilder.setConfig(requestConfigBuilder.build());
        HttpUriRequest httpUriRequest = requestBuilder.build();
        if (request.getHeaders() != null && !request.getHeaders().isEmpty()) {
            for (Map.Entry<String, String> header : request.getHeaders().entrySet()) {
                httpUriRequest.addHeader(header.getKey(), header.getValue());
            }
        }
        return httpUriRequest;
    }


    private RequestBuilder selectRequestMethod(Request request) {
        String method = request.getMethod();
        if (method == null || method.equalsIgnoreCase(HttpConstant.Method.GET)) {
            //default get
            return RequestBuilder.get();
        } else if (method.equalsIgnoreCase(HttpConstant.Method.POST)) {
            return addFormParams(RequestBuilder.post(),request);
        } else if (method.equalsIgnoreCase(HttpConstant.Method.HEAD)) {
            return RequestBuilder.head();
        } else if (method.equalsIgnoreCase(HttpConstant.Method.PUT)) {
            return addFormParams(RequestBuilder.put(), request);
        } else if (method.equalsIgnoreCase(HttpConstant.Method.DELETE)) {
            return RequestBuilder.delete();
        } else if (method.equalsIgnoreCase(HttpConstant.Method.TRACE)) {
            return RequestBuilder.trace();
        }
        throw new IllegalArgumentException("Illegal HTTP Method " + method);
    }

    private RequestBuilder addFormParams(RequestBuilder requestBuilder, Request request) {
        if (request.getRequestBody() != null) {
            ByteArrayEntity entity = new ByteArrayEntity(request.getRequestBody().getBody());
            entity.setContentType(request.getRequestBody().getContentType());
            requestBuilder.setEntity(entity);
        }
        return requestBuilder;
    }


    private HttpClientContext convertHttpClientContext(Request request) {
        HttpClientContext httpContext = new HttpClientContext();
        Site site = request.getSiteInfo();
//        if (proxy != null && proxy.getUsername() != null) {
//            AuthState authState = new AuthState();
//            authState.update(new BasicScheme(ChallengeState.PROXY), new UsernamePasswordCredentials(proxy
// .getUsername(), proxy.getPassword()));
//            httpContext.setAttribute(HttpClientContext.PROXY_AUTH_STATE, authState);
//        }
        if (request.getCookies() != null && !request.getCookies().isEmpty()) {
            CookieStore cookieStore = new BasicCookieStore();
            for (Map.Entry<String, String> cookieEntry : request.getCookies().entrySet()) {
                BasicClientCookie cookie1 = new BasicClientCookie(cookieEntry.getKey(), cookieEntry.getValue());
                cookie1.setDomain(WebcrawlerUtils.removePort(WebcrawlerUtils.getDomain(request.getUrl())));
                cookieStore.addCookie(cookie1);
            }
            httpContext.setCookieStore(cookieStore);
        }
        return httpContext;
    }
}
