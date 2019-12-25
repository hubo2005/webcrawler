package com.web.crawler.webcrawler.constant;

import com.web.crawler.webcrawler.spider.SpiderJob;

import java.util.concurrent.DelayQueue;

public class CrawlerConstants {

    public static DelayQueue<SpiderJob> jobList = new DelayQueue<SpiderJob>();

}
