package com.javaedge.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.javaedge.entity.StockCounts;
import com.javaedge.entity.USStockRss;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @ClassName USStockRssMapper
 * @Author JavaEdge
 * @Version 1.0
 * @Description USStockRssMapper
 **/
public interface USStockRssMapper extends BaseMapper<USStockRss> {

    public List<StockCounts> queryStockCountsBetweenDate(@Param("paramMap") Map<String, Object> map);

    public List<USStockRss> queryStockByTitleKeywords(@Param("keywords") List<String> titleKeywords);

}
