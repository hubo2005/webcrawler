package com.web.crawler.webcrawler.spider.novel;


import com.web.crawler.webcrawler.constant.CrawlerConstants;
import com.web.crawler.webcrawler.model.Site;
import com.web.crawler.webcrawler.spider.SpiderJob;
import org.springframework.stereotype.Component;

@Component
public class NovelCrawler{


    private static NovelCrawler novelCrawler;

    public static NovelCrawler getInstance() {
        if (novelCrawler==null) {
            novelCrawler = new NovelCrawler();
        }
        return novelCrawler;
    }

    public void loadInitialPages() {
        Site piaotianSite = new Site();
        piaotianSite.setDomain("www.piaotian5.com");
        piaotianSite.setStartURL("https://www.piaotian5.com");
        SpiderJob novalCrawlJob = new SpiderJob(piaotianSite);
        CrawlerConstants.jobList.put(novalCrawlJob);
    }
}
