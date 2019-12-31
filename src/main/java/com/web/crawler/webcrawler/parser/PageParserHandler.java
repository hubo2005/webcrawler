package com.web.crawler.webcrawler.parser;

import com.web.crawler.webcrawler.constant.CrawlerConstants;
import com.web.crawler.webcrawler.model.Page;
import com.web.crawler.webcrawler.processer.PageProcessor;
import com.web.crawler.webcrawler.spider.ParsePageJob;
import com.web.crawler.webcrawler.threadpool.ThreadPool;
import com.web.crawler.webcrawler.utils.DummyURLUtil;
import org.apache.dubbo.common.extension.ExtensionLoader;

import java.util.concurrent.ExecutorService;

public class PageParserHandler extends Thread {

    protected ExecutorService executorService;

    public PageParserHandler() {
    }

    @Override
    public void run() {
        ParsePageJob parsePageJob;
        while (true) {
            try {
                parsePageJob = CrawlerConstants.parsePageJobs.take();
                if (null != parsePageJob) {
                    parsePage(parsePageJob);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

    private void parsePage(ParsePageJob parsePageJob) {
        if (parsePageJob != null && parsePageJob.getJobs() != null && parsePageJob.getJobs().size() > 0) {
            for (Object job : parsePageJob.getJobs()) {
                Page page = (Page) job;
                parseThePage(page);
            }
        }
    }

    private void parseThePage(Page page) {
//        ExecutorService executor = getExecutorService();
//        executor.execute(new SpiderRunnable(page));
        PageProcessor pageProcessor = (PageProcessor) ExtensionLoader.getExtensionLoader(PageProcessor.class).getAdaptiveExtension();
        pageProcessor.process(page.getDubboURL(),page);
    }

    public ExecutorService getExecutorService() {
        if (executorService == null) {
            executorService =
                    (ExecutorService) ExtensionLoader.getExtensionLoader(ThreadPool.class).getAdaptiveExtension().getExecutor(DummyURLUtil.getURL());
            return executorService;
        }
        return executorService;

    }
}
