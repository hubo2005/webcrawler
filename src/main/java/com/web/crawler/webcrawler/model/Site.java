package com.web.crawler.webcrawler.model;

import com.web.crawler.webcrawler.utils.DummyURLUtil;
import com.web.crawler.webcrawler.utils.HttpConstant;
import org.apache.dubbo.common.URL;
import org.springframework.util.StringUtils;

import java.util.*;

public class Site {

    private String startURL;

    private String method;

    private List<Request> siteRequests;

    private String domain;

    private String userAgent;

    private Map<String, String> defaultCookies = new LinkedHashMap<String, String>();

    private Map<String, Map<String, String>> cookies = new HashMap<String, Map<String, String>>();

    private String charset;

    private int sleepTime = 5000;

    private int retryTimes = 0;

    private int cycleRetryTimes = 0;

    private int retrySleepTime = 1000;

    private int timeOut = 5000;

    private Map<String, String> headers = new HashMap<String, String>();

    private boolean useGzip = true;

    private boolean disableCookieManagement = false;

    private static final Set<Integer> DEFAULT_STATUS_CODE_SET = new HashSet<Integer>();

    private int maxCrawlURLSize = 100;

    private String siteCategory;

    static {
        DEFAULT_STATUS_CODE_SET.add(HttpConstant.StatusCode.CODE_200);
    }


    public String getMethod() {
        return method;
    }

    public Site setMethod(String method) {
        this.method = method;
        return this;
    }

    public String getStartURL() {
        return startURL;
    }

    public Site setStartURL(String startURL) {
        this.startURL = startURL;
        return this;
    }

    public List<Request> getSiteRequests() {
        return siteRequests;
    }

    public Site setSiteRequests(List<Request> siteRequests) {
        this.siteRequests = siteRequests;
        return this;
    }

    public String getDomain() {
        return domain;
    }

    public Site setDomain(String domain) {
        this.domain = domain;
        return this;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public Site setUserAgent(String userAgent) {
        this.userAgent = userAgent;
        return this;
    }

    public Map<String, String> getDefaultCookies() {
        return defaultCookies;
    }

    public Site setDefaultCookies(Map<String, String> defaultCookies) {
        this.defaultCookies = defaultCookies;
        return this;
    }

    public Map<String, Map<String, String>> getCookies() {
        return cookies;
    }

    public Site setCookies(Map<String, Map<String, String>> cookies) {
        this.cookies = cookies;
        return this;
    }

    public String getCharset() {
        return charset;
    }

    public Site setCharset(String charset) {
        this.charset = charset;
        return this;
    }

    public int getSleepTime() {
        return sleepTime;
    }

    public Site setSleepTime(int sleepTime) {
        this.sleepTime = sleepTime;
        return this;
    }

    public int getRetryTimes() {
        return retryTimes;
    }

    public Site setRetryTimes(int retryTimes) {
        this.retryTimes = retryTimes;
        return this;
    }

    public int getCycleRetryTimes() {
        return cycleRetryTimes;
    }

    public Site setCycleRetryTimes(int cycleRetryTimes) {
        this.cycleRetryTimes = cycleRetryTimes;
        return this;
    }

    public int getRetrySleepTime() {
        return retrySleepTime;
    }

    public Site setRetrySleepTime(int retrySleepTime) {
        this.retrySleepTime = retrySleepTime;
        return this;
    }

    public int getTimeOut() {
        return timeOut;
    }

    public Site setTimeOut(int timeOut) {
        this.timeOut = timeOut;
        return this;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Site setHeaders(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public boolean isUseGzip() {
        return useGzip;
    }

    public Site setUseGzip(boolean useGzip) {
        this.useGzip = useGzip;
        return this;
    }

    public boolean isDisableCookieManagement() {
        return disableCookieManagement;
    }

    public Site setDisableCookieManagement(boolean disableCookieManagement) {
        this.disableCookieManagement = disableCookieManagement;
        return this;
    }


    public int getMaxCrawlURLSize() {
        return maxCrawlURLSize;
    }

    public void setMaxCrawlURLSize(int maxCrawlURLSize) {
        this.maxCrawlURLSize = maxCrawlURLSize;
    }


    public URL getURL() {
        if (StringUtils.isEmpty(siteCategory)) {
            return DummyURLUtil.getURL().addParameter("type","novel");
        }

        return DummyURLUtil.getURL().addParameter("type",siteCategory);
    }


}
