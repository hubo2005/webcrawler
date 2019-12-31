package com.web.crawler.webcrawler.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebcrawlerUtils {

    private static final Pattern patternForCharset = Pattern.compile("charset\\s*=\\s*['\"]*([^\\s;'\"]*)",
            Pattern.CASE_INSENSITIVE);
    private static Pattern patternForProtocal = Pattern.compile("[\\w]+://");

    /**
     * canonicalizeUrl
     * <br>
     * Borrowed from Jsoup.
     *
     * @param url   url
     * @param refer refer
     * @return canonicalizeUrl
     */
    public static String canonicalizeUrl(String url, String refer) {
        URL base;
        try {
            try {
                base = new URL(refer);
            } catch (MalformedURLException e) {
                // the base is unsuitable, but the attribute may be abs on its own, so try that
                URL abs = new URL(refer);
                return abs.toExternalForm();
            }
            // workaround: java resolves '//path/file + ?foo' to '//path/?foo', not '//path/file?foo' as desired
            if (url.startsWith("?"))
                url = base.getPath() + url;
            URL abs = new URL(base, url);
            return abs.toExternalForm();
        } catch (MalformedURLException e) {
            return "";
        }
    }

    /**
     * @param url url
     * @return new url
     * @deprecated
     */
    public static String encodeIllegalCharacterInUrl(String url) {
        return url.replace(" ", "%20");
    }

    public static String fixIllegalCharacterInUrl(String url) {
        //TODO more charator support
        return url.replace(" ", "%20").replaceAll("#+", "#");
    }

    public static String getHost(String url) {
        String host = url;
        int i = StringUtils.ordinalIndexOf(url, "/", 3);
        if (i > 0) {
            host = StringUtils.substring(url, 0, i);
        }
        return host;
    }

    public static String removeProtocol(String url) {
        return patternForProtocal.matcher(url).replaceAll("");
    }

    public static String getDomain(String url) {
        String domain = removeProtocol(url);
        int i = StringUtils.indexOf(domain, "/", 1);
        if (i > 0) {
            domain = StringUtils.substring(domain, 0, i);
        }
        return removePort(domain);
    }

    public static String removePort(String domain) {
        int portIndex = domain.indexOf(":");
        if (portIndex != -1) {
            return domain.substring(0, portIndex);
        } else {
            return domain;
        }
    }

    public static String getCharset(String contentType) {
        Matcher matcher = patternForCharset.matcher(contentType);
        if (matcher.find()) {
            String charset = matcher.group(1);
            if (Charset.isSupported(charset)) {
                return charset;
            }
        }
        return null;
    }

    public static String detectCharset(String contentType, byte[] contentBytes) throws IOException {
        String charset;
        // charset
        // 1、encoding in http header Content-Type
        charset = WebcrawlerUtils.getCharset(contentType);
        if (StringUtils.isNotBlank(contentType) && StringUtils.isNotBlank(charset)) {
          //  logger.debug("Auto get charset: {}", charset);
            return charset;
        }
        // use default charset to decode first time
        Charset defaultCharset = Charset.defaultCharset();
        String content = new String(contentBytes, defaultCharset);
        // 2、charset in meta
        if (StringUtils.isNotEmpty(content)) {
            Document document = Jsoup.parse(content);
            Elements links = document.select("meta");
            for (Element link : links) {
                // 2.1、html4.01 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
                String metaContent = link.attr("content");
                String metaCharset = link.attr("charset");
                if (metaContent.indexOf("charset") != -1) {
                    metaContent = metaContent.substring(metaContent.indexOf("charset"), metaContent.length());
                    charset = metaContent.split("=")[1];
                    break;
                }
                // 2.2、html5 <meta charset="UTF-8" />
                else if (StringUtils.isNotEmpty(metaCharset)) {
                    charset = metaCharset;
                    break;
                }
            }
        }
       // logger.debug("Auto get charset: {}", charset);
        // 3、todo use tools as cpdetector for content decode
        return charset;
    }
    public static Map<String,List<String>> convertHeaders(Header[] headers){
        Map<String,List<String>> results = new HashMap<String, List<String>>();
        for (Header header : headers) {
            List<String> list = results.get(header.getName());
            if (list == null) {
                list = new ArrayList<String>();
                results.put(header.getName(), list);
            }
            list.add(header.getValue());
        }
        return results;
    }
}
