package com.web.crawler.webcrawler.spider;

import com.web.crawler.webcrawler.threadpool.ThreadPool;
import com.web.crawler.webcrawler.type.Crawler;
import com.web.crawler.webcrawler.type.Site;
import com.web.crawler.webcrawler.type.WebResource;
import com.web.crawler.webcrawler.utils.Constants;
import com.web.crawler.webcrawler.utils.DummyURLUtil;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

@Component
public class SpiderService {

    protected ExecutorService executorService;

    public void startCrawl(WebResource webResource) {

        List<WebResource> splitedWebResource = splitWebResource(webResource);

        ExecutorService executor = getExecutorService();

        if (splitedWebResource!=null) {
            for (WebResource resource : splitedWebResource) {
                executor.execute(new SpiderRunnable(resource));
            }

        }

    }


    private List<WebResource> splitWebResource(WebResource webResource) {
        List<WebResource> splitList = new ArrayList<>();
        if (webResource.getCrawlSites()==null ||  webResource.getCrawlSites().size()==0) {
            return null;
        }
        List<List<Site>> choppedSite = chopped(webResource.getCrawlSites(), Constants.BATCH_NO);
        for (List<Site> sites : choppedSite) {
            WebResource choppedWebResource = new WebResource();
            BeanUtils.copyProperties(webResource,choppedWebResource);
            choppedWebResource.setCrawlSites(sites);
            splitList.add(choppedWebResource);
        }
        return splitList;

    }

    public <T> List<List<T>> chopped(List<T> list, int size) {
        List<List<T>> choppedList = new ArrayList<>();
        int totalSize = list.size();
        for (int i=0; i<totalSize; i +=size) {
            choppedList.add(new ArrayList<T>(
                    list.subList(i,Math.min(totalSize,i+size))
            ));
        }
        return choppedList;
    }

    public ExecutorService getExecutorService(){
        if (executorService ==null) {
            executorService = (ExecutorService) ExtensionLoader.getExtensionLoader(ThreadPool.class).getAdaptiveExtension().getExecutor(DummyURLUtil.getURL());
            return executorService;
        }
        return executorService;

    }


}
