package com.web.crawler.webcrawler.processer;

import com.web.crawler.webcrawler.model.Page;
import com.web.crawler.webcrawler.model.Site;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Adaptive;
import org.apache.dubbo.common.extension.SPI;


@SPI
public interface PageProcessor {

    /**
     * process the page, extract urls to fetch, extract the data and store
     *
     * @param page page
     */
    @Adaptive
    public void process(URL url, Page page);

    /**
     * get the site settings
     *
     * @return site
     * @see Site
     */
    public Site getSite();
}
