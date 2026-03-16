package com.javaedge.service;

import com.javaedge.entity.USStockMsg;

import java.util.List;

/**
 * @ClassName TelegramBotService
 * @Author JavaEdge
 * @Version 1.0
 * @Description TelegramBotService
 **/
public interface TelegramBotService {

    /**
     * @Description: 用于发送单个消息（测试）
     * @Author JavaEdge
     * @param text
     */
    public void sendMessage(String text);

    /**
     * @Description: 发送消息
     * @Author JavaEdge
     * @param msgList
     */
    public void sendMessage(List<USStockMsg> msgList) throws Exception;

}
