package com.web.crawler.webcrawler.utils;

import org.apache.dubbo.common.URL;

public class DummyURLUtil {
    public static URL getURL(){
        URL staticURL = URL.valueOf("dubbo://127.0.0.1:8080");
        return staticURL;
    }
}
