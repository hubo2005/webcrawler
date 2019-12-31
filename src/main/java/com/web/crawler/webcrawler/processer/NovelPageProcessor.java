package com.web.crawler.webcrawler.processer;

import com.web.crawler.webcrawler.model.Page;
import com.web.crawler.webcrawler.model.Site;
import org.apache.dubbo.common.URL;

public class NovelPageProcessor implements PageProcessor {

    @Override
    public void process(URL dubboURL, Page page) {
        System.out.println("page==="+ page.toString());
    }

    @Override
    public Site getSite() {
        return null;
    }
}
