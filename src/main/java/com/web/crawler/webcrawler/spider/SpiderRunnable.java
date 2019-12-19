package com.web.crawler.webcrawler.spider;

import com.web.crawler.webcrawler.type.Crawler;
import com.web.crawler.webcrawler.type.WebResource;
import org.apache.dubbo.common.extension.ExtensionLoader;

public class SpiderRunnable implements Runnable {

    private final WebResource resource;

    public SpiderRunnable(WebResource webResource) {
        this.resource = webResource;
    }

    @Override
    public void run() {
        Crawler crawler = ExtensionLoader.getExtensionLoader(Crawler.class).getAdaptiveExtension();
        crawler.crawlData(resource.getURL(), resource);
    }
}
