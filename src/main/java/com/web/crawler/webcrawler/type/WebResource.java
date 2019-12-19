package com.web.crawler.webcrawler.type;

import com.web.crawler.webcrawler.utils.DummyURLUtil;
import org.apache.dubbo.common.URL;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

public class WebResource {

    private Map<String,String> parameters;
    private String resourceType;
    private List<Site> crawlSites;

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public List<Site> getCrawlSites() {
        return crawlSites;
    }

    public void setCrawlSites(List<Site> crawlSites) {
        this.crawlSites = crawlSites;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public String getParameter(String key) {
        return this.parameters.get(key);
    }


    public URL getURL() {
        if (StringUtils.isEmpty(resourceType)) {
            return DummyURLUtil.getURL().addParameter("type","novel");
        }

        return DummyURLUtil.getURL().addParameter("type",resourceType);
    }
}
