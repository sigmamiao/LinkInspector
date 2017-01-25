package com.sigma.linkinspector.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sigma.linkinspector.core.LinkCrawler;
import com.sigma.linkinspector.model.Result;
import com.sigma.linkinspector.queue.LinkQueue;
import com.sigma.linkinspector.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created with IDEA
 * User: Omega
 * Date: 2017/1/25
 * Time: 17:34
 */
public class WebLinkTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebLinkTest.class);

    private LinkCrawler linkCrawler;
    private ExecutorService executorService;
    private long startTime;
    private long endTime;

    @BeforeClass
    public void beforeClass() {
        this.linkCrawler = new LinkCrawler();
        this.executorService = Executors.newSingleThreadExecutor();
    }

    @Test(description = "XXX Online All Links Check")
    public void test() {
        this.startTime = System.currentTimeMillis();

        Thread thread = new Thread(() -> {

            try {
                WebLinkTest.this.linkCrawler.crawling(WebLinkTest.this.linkCrawler.getHost());
            } catch (Exception e) {
                WebLinkTest.LOGGER.error(e.getMessage());
                throw new RuntimeException("线程被中断!");
            }

        });

        this.executorService.execute(thread);
        this.executorService.shutdown();
        while (!this.executorService.isTerminated()) {
            //empty loop
        }

    }

    @AfterClass
    public void afterClass() {
        this.endTime = System.currentTimeMillis();
        long totalTime = (this.endTime - this.startTime) / 1000;
        int totalLinks = LinkQueue.getTotalLinksNum();
        WebLinkTest.LOGGER.info("共发现: " + String.valueOf(totalLinks) + " 个链接");
        WebLinkTest.LOGGER.info("共用时: " + String.valueOf(totalTime) + " 秒");
        WebLinkTest.LOGGER.error("异常链接如下, 共 " + LinkQueue.getDealLinkNum() + "个");

        Set<String> deadlinks = LinkQueue.getDeadLinks();

        ObjectMapper objectMapper = new ObjectMapper();
        Result result = new Result();
        result.setDeadLinks(deadlinks);

        try {
            objectMapper.writeValue(new File(System.getProperty("user.dir") + File.separator + "deadlink.json"),
                    result);
            if (0 == deadlinks.size()) {
                FileUtils.writeToHtmlReportNoProblem();
            } else {
                FileUtils.writeToHtmlReportHasProblem(deadlinks);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String deadLink : deadlinks) {
            WebLinkTest.LOGGER.error("发现异常链接: {}", deadLink);
        }

        Assert.assertEquals(deadlinks.size(), 0);
    }
}
