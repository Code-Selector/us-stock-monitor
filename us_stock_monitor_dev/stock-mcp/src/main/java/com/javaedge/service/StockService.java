package com.javaedge.service;

import com.javaedge.entity.StockCounts;
import com.javaedge.entity.USStockRss;

import java.util.List;

/**
 * @ClassName RssService
 * @Author JavaEdge
 * @Version 1.0
 * @Description RssService
 **/
public interface StockService {

    /**
     * @Description: 根据股票代码查询股票数据
     * @Author JavaEdge
     * @param stockCode
     */
    public List<USStockRss> queryStock(String stockCode);

    /**
     * @Description: 查询时间段内的股票数据
     * @Author JavaEdge
     * @param stockCode
     * @param startDate
     * @param endDate
     * @return List<USStockRss>
     */
    public List<USStockRss> queryStockBetweenDate(String stockCode, String startDate, String endDate);

    /**
     * @Description: 查询指定日期段内异动次数超过指定次数
     * @Author JavaEdge
     * @param targetCounts
     * @param startDate
     * @param endDate
     * @return List<StockCounts>
     */
    public List<StockCounts> queryStockCountsBetweenDate(Integer targetCounts, String startDate, String endDate);

    /**
     * @Description: 根据标题关键字查询股票数据
     * @Author JavaEdge
     * @param titleKeywords
     * @return List<USStockRss>
     */
    public List<USStockRss> queryStockByTitleKeywords(List<String> titleKeywords);
}
