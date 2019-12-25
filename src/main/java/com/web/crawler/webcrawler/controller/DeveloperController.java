package com.web.crawler.webcrawler.controller;


import com.web.crawler.webcrawler.spider.novel.NovelCrawler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeveloperController {

    @Autowired
    NovelCrawler novelCrawler;

    @GetMapping("/load")
    public String loadData() {
        novelCrawler.loadInitialPages();
        return "done";
    }
}
