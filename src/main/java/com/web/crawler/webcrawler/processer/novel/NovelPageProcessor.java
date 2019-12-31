package com.web.crawler.webcrawler.processer.novel;

import com.web.crawler.webcrawler.model.Page;
import com.web.crawler.webcrawler.model.Site;
import com.web.crawler.webcrawler.processer.PageProcessor;
import com.web.crawler.webcrawler.processer.novel.rules.PiaotianRules;
import org.apache.dubbo.common.URL;


public class NovelPageProcessor implements PageProcessor {

    @Override
    public void process(URL dubboURL, Page page) {

        for (String xpathString : PiaotianRules.xPathSelector) {
            System.out.println(page.getHtml().xpath(xpathString).all());
        }

        //Document document = Jsoup.parse(page.getRawText());
        //System.out.println(document.getElementsByTag("title"));
    }

    @Override
    public Site getSite() {
        return null;
    }

}
