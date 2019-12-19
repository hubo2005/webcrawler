package com.web.crawler.webcrawler.type;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Adaptive;
import org.apache.dubbo.common.extension.SPI;

@SPI
public interface Crawler {


    @Adaptive("type")
    void crawlData(URL url, WebResource webResource);

    void parseData();

}
