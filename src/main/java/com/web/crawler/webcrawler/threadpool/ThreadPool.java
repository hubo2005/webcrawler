package com.web.crawler.webcrawler.threadpool;


import com.web.crawler.webcrawler.utils.Constants;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Adaptive;
import org.apache.dubbo.common.extension.SPI;

import java.util.concurrent.Executor;

@SPI("cached")
public interface ThreadPool {

    @Adaptive({Constants.THREADPOOL_KEY})
    Executor getExecutor(URL url);
}
