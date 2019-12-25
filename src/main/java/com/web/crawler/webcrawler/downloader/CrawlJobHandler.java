package com.web.crawler.webcrawler.downloader;


import com.web.crawler.webcrawler.constant.CrawlerConstants;
import com.web.crawler.webcrawler.model.Request;
import com.web.crawler.webcrawler.model.Site;
import com.web.crawler.webcrawler.spider.SpiderJob;
import com.web.crawler.webcrawler.spider.SpiderRunnable;
import com.web.crawler.webcrawler.threadpool.ThreadPool;
import com.web.crawler.webcrawler.utils.DummyURLUtil;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class CrawlJobHandler extends Thread{

    protected ExecutorService executorService;

    public CrawlJobHandler() {
    }

    @Override
    public void run() {
        SpiderJob spiderJob;
        while (true) {
            try {
                spiderJob = CrawlerConstants.jobList.take();
                if (null!= spiderJob) {
                    processJob(spiderJob);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
      
        }

    }

    private void processJob(SpiderJob spiderJob) {
        if (spiderJob!=null && spiderJob.getJobs()!=null && spiderJob.getJobs().size()>0) {
            for (Object job: spiderJob.getJobs()) {
                Site spiderSite = (Site)job;
                List<Site> splitedSites = splitSiteBasedOnRequestURLNumberLimit(spiderSite);
                fireCrawlThread(splitedSites);
            }
        }
    }

    private void fireCrawlThread(List<Site> splitedSites) {
        ExecutorService executor = getExecutorService();
        //todo： add threadpool queue length check, to avoid flooding the threadpool
        for (Site site : splitedSites) {
            executor.execute(new SpiderRunnable(site));
        }
    }

    private List<Site> splitSiteBasedOnRequestURLNumberLimit(Site spiderSite) {
        if (spiderSite==null ||  (StringUtils.isEmpty(spiderSite.getStartURL()) && spiderSite.getSiteRequests().isEmpty())) {
            return null;
        }

        return chopped(spiderSite);
    }


    public List<Site> chopped(Site spiderSite) {
        List<Site> choppedList = new ArrayList<>();
        //将starturl放到request里面
        if (!StringUtils.isEmpty(spiderSite.getStartURL())) {
            Site newSite = new Site();
            BeanUtils.copyProperties(spiderSite,newSite);
            newSite.setSiteRequests(new ArrayList<>());
            newSite.getSiteRequests().add(convertStartURLToRequest(spiderSite));
            choppedList.add(newSite);
        }
        //将request根据预定的大小拆分
        if (spiderSite.getSiteRequests()!=null && spiderSite.getSiteRequests().size()>0) {
            List<Request> requestList = spiderSite.getSiteRequests();
            int totalSize = spiderSite.getSiteRequests().size();
            int stepSize = spiderSite.getMaxCrawlURLSize();
            for (int i = 0; i < totalSize; i += stepSize) {
                Site choppedRequestSite = new Site();
                BeanUtils.copyProperties(spiderSite,choppedRequestSite);
                List<Request> newRequestList = new ArrayList<>(requestList.subList(i,Math.min(totalSize,i+stepSize)));
                choppedRequestSite.setSiteRequests(newRequestList);
                choppedList.add(choppedRequestSite);
            }
        }
        return choppedList;
    }

    private Request convertStartURLToRequest(Site spiderSite) {
        Request request = new Request();
        request.setUrl(spiderSite.getStartURL());
        return request;
    }

    public ExecutorService getExecutorService(){
        if (executorService ==null) {
            executorService = (ExecutorService) ExtensionLoader.getExtensionLoader(ThreadPool.class).getAdaptiveExtension().getExecutor(DummyURLUtil.getURL());
            return executorService;
        }
        return executorService;

    }
}
