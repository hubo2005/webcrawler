package com.web.crawler.webcrawler.spider;


import com.web.crawler.webcrawler.downloader.HttpClientDownloader;
import com.web.crawler.webcrawler.model.Page;
import com.web.crawler.webcrawler.model.Request;
import com.web.crawler.webcrawler.model.Site;
import com.web.crawler.webcrawler.utils.SpringUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class SpiderRunnable implements Runnable {

    private final Site site;

    @Autowired
    HttpClientDownloader downloader;

    public SpiderRunnable(Site site) {
        this.site = site;
    }

    @Override
    public void run() {
        if (downloader ==null) {
            downloader = SpringUtils.getBean(HttpClientDownloader.class);
        }
        for (Request request : site.getSiteRequests()) {
            Page page = downloader.download(request);
        }
        //System.out.println(crawlTask.getCrawlSites().size());
        System.out.println("print in abstract");
    }
}
