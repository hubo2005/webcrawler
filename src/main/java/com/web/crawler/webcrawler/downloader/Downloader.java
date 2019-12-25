package com.web.crawler.webcrawler.downloader;

import com.web.crawler.webcrawler.model.Page;
import com.web.crawler.webcrawler.model.Request;


public interface Downloader {

    Page download(Request request);
}
