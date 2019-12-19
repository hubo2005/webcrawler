package com.web.crawler.webcrawler.controller;


import com.web.crawler.webcrawler.type.Crawler;
import com.web.crawler.webcrawler.utils.DummyURLUtil;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeveloperController {

    @GetMapping("/load")
    public String loadData(){
        Crawler crawler = ExtensionLoader.getExtensionLoader(Crawler.class).getAdaptiveExtension();

        URL dubboURL = DummyURLUtil.getURL().addParameter("type","novel");
        crawler.crawlData(dubboURL,null);

        URL dubboURL1 = DummyURLUtil.getURL().addParameter("type","stock");
        crawler.crawlData(dubboURL1,null);
        return "done";
    }
}
