package com.sigma.linkinspector.core;

import com.beust.jcommander.internal.Sets;
import com.google.common.collect.Maps;
import com.sigma.linkinspector.handler.ConfigHandler;
import com.sigma.linkinspector.queue.LinkQueue;
import com.sigma.linkinspector.util.ToolsUtils;
import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;

/**
 * Created with IDEA
 * User: Omega
 * Date: 2017/1/25
 * Time: 16:56
 */
public class LinkCrawler {

    private static final Logger LOGGER = LoggerFactory.getLogger(LinkCrawler.class);

    private String host;

    private String httpsHost;

    private String loginUrl;

    private String phone;

    private String password;

    public LinkCrawler() {
        Map<String, String> configInfo = ConfigHandler.fetchConfigInfo();

        String url = configInfo.get("url");
        this.setHost(url);
        this.setLoginUrl(url + "xxx.login");
        this.setPhone(configInfo.get("phone"));
        this.setPassword(configInfo.get("password"));
    }

    //


    private Map<String, String> cookies = Maps.newHashMap();

    private void init(String seedUrl) throws Exception {
        LinkQueue.addUnVisitedUrl(seedUrl);

        try {

            Map<String, String> loginMap = this.getLoginFormParamMap();

            Connection.Response response = Jsoup.connect(this.loginUrl).followRedirects(true).header("Accept", "*/*")
                    .header("Accept-Encoding", "gzip, deflate, sdch").header("Accept-Language", "zh-CN,zh;q=0.8")
                    .header("User-Agent",
                            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36")
                    .header("Referer", this.httpsHost + "/")
                    .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8").data(loginMap)
                    .method(Connection.Method.POST).execute();

            this.setCookies(response.cookies());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }


    public void crawling(final String url) throws Exception {

        new LinkFilter() {
            @Override
            public boolean accept(String url) {
                return (url.contains(LinkCrawler.this.host) || url.startsWith(LinkCrawler.this.httpsHost));
            }
        };

        this.init(url);

        while (!LinkQueue.isEmptyOfUnVisitedUrl() && LinkQueue.getVisitedUrlNum() <= 1500000) {
            String prepareVisitUrl = LinkQueue.unVisitedUrlDeQueue();
            Set<String> links = this.extractLinks(prepareVisitUrl);
            LinkQueue.addVisitedUrl(prepareVisitUrl);// add 2 visited queue

            for (String link : links) {
                if (ToolsUtils.urlCheck(link)) {
                    LinkQueue.addUnVisitedUrl(link);
                }
            }
        }

    }

    //

    private synchronized Set<String> extractLinks(String url) {
        Set<String> resultLinks = Sets.newLinkedHashSet();
        try {

            Connection.Response resp = Jsoup.connect(url).timeout(50000)
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                    .header("Accept-Encoding", "gzip, deflate, sdch").header("Accept-Language", "zh-CN,zh;q=0.8")
                    .header("Connection", "keep-alive").header("Referer", this.host + "/")
                    .header("User-Agent",
                            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36")
                    .followRedirects(false).cookies(this.cookies).method(Connection.Method.GET).execute();
            // System.out.println(url);

            Document doc = resp.parse();

            LinkCrawler.LOGGER.info("fetch: " + url);

            Elements links = doc.select("a[href]");

            for (Element link : links) {
                String linkHref = link.attr("abs:href");

                if (ToolsUtils.urlCheck(linkHref)) {
                    resultLinks.add(linkHref);
                }
            } // end for
        } catch (HttpStatusException e) {
            LinkCrawler.LOGGER.error("Http状态码不正确: " + e.getMessage());
            LinkQueue.addLink2DeadList(url);
        } catch (Exception e) {
            LinkCrawler.LOGGER.error(e.getMessage());
            LinkQueue.addLink2DeadList(url);
        }
        return resultLinks;
    }


    private Map<String, String> getLoginFormParamMap() {
        Map<String, String> loginParamMap = Maps.newHashMap();
        loginParamMap.put("username", "xxx");
        loginParamMap.put("password", "xxx");
        return loginParamMap;
    }


    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getHttpsHost() {
        return httpsHost;
    }

    public void setHttpsHost(String httpsHost) {
        this.httpsHost = httpsHost;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public void setCookies(Map<String, String> cookies) {
        this.cookies = cookies;
    }
}

