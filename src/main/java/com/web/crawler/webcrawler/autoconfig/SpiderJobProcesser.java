package com.web.crawler.webcrawler.autoconfig;

import com.web.crawler.webcrawler.downloader.CrawlJobHandler;
import com.web.crawler.webcrawler.parser.PageParserHandler;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class SpiderJobProcesser implements ApplicationListener<ApplicationReadyEvent> {

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.execute(new CrawlJobHandler());
        executor.execute(new PageParserHandler());
    }
}
