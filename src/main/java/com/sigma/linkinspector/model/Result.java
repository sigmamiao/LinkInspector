package com.sigma.linkinspector.model;

import java.util.Set;

/**
 * Created with IDEA
 * User: Omega
 * Date: 2017/1/25
 * Time: 16:50
 */
public class Result {

    private Set<String> deadLinks;

    public void setDeadLinks(Set<String> deadLinks) {
        this.deadLinks = deadLinks;
    }

    public Set<String> getDeadLinks() {
        return this.deadLinks;
    }

    @Override
    public String toString() {
        return "{\"deadLinks\":\"" + this.deadLinks + "\"}";
    }
}
