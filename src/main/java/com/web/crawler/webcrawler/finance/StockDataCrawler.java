package com.web.crawler.webcrawler.finance;

import com.web.crawler.webcrawler.type.Crawler;
import com.web.crawler.webcrawler.type.WebResource;
import org.apache.dubbo.common.URL;

public class StockDataCrawler implements Crawler {

    @Override
    public void crawlData(URL url, WebResource webResource) {
        System.out.println("stock data crawleerrrrrrrrrrrrrrr");
    }

    @Override
    public void parseData() {

    }
}
