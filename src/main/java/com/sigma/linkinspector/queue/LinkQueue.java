package com.sigma.linkinspector.queue;

import com.sigma.linkinspector.util.EncryptUtils;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created with IDEA
 * User: Omega
 * Date: 2017/1/25
 * Time: 16:45
 */
public class LinkQueue {

    private static HashSet<String> visitedUrlSet = new HashSet<>();
    private static LinkedBlockingQueue<String> unVisitUrlQueue = new LinkedBlockingQueue<>();
    private static HashSet<String> deadLinks = new LinkedHashSet<>();

    public static LinkedBlockingQueue<String> getUnVisitUrlqueue() {
        return LinkQueue.unVisitUrlQueue;
    }

    public static void addVisitedUrl(String url) {

        LinkQueue.visitedUrlSet.add(EncryptUtils.makeMD5Encrypt(url));
    }

    public static boolean visitedLinkSetContains(String url) {
        return LinkQueue.visitedUrlSet.contains(EncryptUtils.makeMD5Encrypt(url));
    }

    public static boolean unVisitedLinkQueueContains(String url) {
        return LinkQueue.unVisitUrlQueue.contains(url);
    }

    public static void removeVisitedUrl(String url) {
        LinkQueue.visitedUrlSet.remove(url);
    }

    public static String unVisitedUrlDeQueue() throws InterruptedException {
        return LinkQueue.unVisitUrlQueue.take();
    }

    public static void addUnVisitedUrl(String url) throws InterruptedException {
        if (LinkQueue.validateAdd2UnVisitedUrlQueue(url)) {
            LinkQueue.unVisitUrlQueue.put(url);
        }
    }

    public static int getVisitedUrlNum() {
        return LinkQueue.visitedUrlSet.size();
    }

    public static boolean isEmptyOfUnVisitedUrl() {
        return LinkQueue.unVisitUrlQueue.isEmpty();
    }

    public static void addLink2DeadList(String url) {
        LinkQueue.deadLinks.add(url);
    }

    private static boolean validateAdd2UnVisitedUrlQueue(String url) {
        return url != null && !"".equals(url.trim()) && !LinkQueue.visitedLinkSetContains(url)
                && !LinkQueue.unVisitedLinkQueueContains(url);
    }

    public static HashSet<String> getDeadLinks() {
        return LinkQueue.deadLinks;
    }

    public static HashSet<String> getVisitedUrlSet() {
        return LinkQueue.visitedUrlSet;
    }

    public static int getDealLinkNum() {
        return LinkQueue.deadLinks.size();
    }

    public static int getTotalLinksNum() {
        return LinkQueue.getVisitedUrlNum() + LinkQueue.getDealLinkNum();
    }
}
