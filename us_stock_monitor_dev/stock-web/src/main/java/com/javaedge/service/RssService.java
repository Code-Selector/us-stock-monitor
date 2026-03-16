package com.javaedge.service;

import com.rometools.rome.feed.synd.SyndEntry;

import java.util.List;

/**
 * @ClassName RssService
 * @Author JavaEdge
 * @Version 1.0
 * @Description RssService
 **/
public interface RssService {

    public void displayRss() throws Exception;

    public List<SyndEntry> fetchRssReed(String rssUrl) throws Exception;

}
