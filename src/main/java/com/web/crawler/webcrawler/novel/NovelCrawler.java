package com.web.crawler.webcrawler.novel;

import com.web.crawler.webcrawler.type.Crawler;
import com.web.crawler.webcrawler.type.WebResource;
import org.apache.dubbo.common.URL;

public class NovelCrawler implements Crawler {

    @Override
    public void crawlData(URL url, WebResource webResource) {

        System.out.println(webResource.getCrawlSites().size());
        System.out.println("novel crawler ====================");
    }

    @Override
    public void parseData() {

    }
}
