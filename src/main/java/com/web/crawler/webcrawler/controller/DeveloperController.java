package com.web.crawler.webcrawler.controller;


import com.web.crawler.webcrawler.spider.SpiderService;
import com.web.crawler.webcrawler.type.Site;
import com.web.crawler.webcrawler.type.WebResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class DeveloperController {

    @Autowired
    SpiderService spiderService;

    @GetMapping("/load")
    public String loadData() {
        spiderService.startCrawl(prepare());
        return "done";
    }


    private WebResource prepare() {
        WebResource webResource = new WebResource();
        webResource.setResourceType("novel");
        webResource.setCrawlSites(getSites());
        return webResource;
    }

    private List<Site> getSites() {
        List<Site> sites = new ArrayList<>();
        for (int i = 0; i < 104; i++) {
            Site site = new Site();
            sites.add(site);
        }
        return sites;
    }
}
