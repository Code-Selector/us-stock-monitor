 package com.javaedge.service;

import com.javaedge.entity.USStockMsg;

import java.util.List;

 /**
  * @ClassName TelegramBotService
  * @Author JavaEdge
  * @Version 1.0
  * @Description TelegramBotService
  **/
 public interface WechatBotService {

     public void sendMessage(String text);

     public void sendMessage(List<USStockMsg> msgList) throws Exception;

 }
