package com.web.crawler.webcrawler.processer.novel.rules;

import java.util.ArrayList;
import java.util.List;

public class PiaotianRules {

    public static List<String> xPathSelector;

    static {
        xPathSelector = new ArrayList<>();
        xPathSelector.add("//a[contains(@href,\"book\")]");
    }
}
