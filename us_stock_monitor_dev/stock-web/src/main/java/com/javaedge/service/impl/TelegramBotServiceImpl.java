package com.javaedge.service.impl;

import com.javaedge.entity.USStockMsg;
import com.javaedge.service.TelegramBotService;
import com.javaedge.utils.TelegramMessageSplitter;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName TelegramBotServiceImpl
 * @Author JavaEdge
 * @Version 1.0
 * @Description TelegramBotServiceImpl
 **/
@Service
public class TelegramBotServiceImpl implements TelegramBotService {

    /**
     * 声明：
     *  禁止使用Telegram进行违反国家法律法规的活动
     */

    private static final String TOKEN = "1234567890:abcxyz_abcdefghijklmnopqrst";   // 替换成你的 token
    private static final String CHAT_ID = "1234567890";                             // 群 chat_id
    private static final String API_URL = "https://api.telegram.org/bot" + TOKEN + "/sendMessage";

    @Resource
    private RestTemplate restTemplate;

    public String formatStockInfoHtml(USStockMsg stock) {
        return "<b>📌 代码: " + stock.getStockCode() + "</b>\n" +
                "📅 时间: " + stock.getPubDateBj() + "\n" +
                "📰 标题: " + stock.getTitleZh() + "\n" +
                "🏷️ 标签: " + stock.getTags() + "\n" +
                "📊 统计: 24小时异动=" + stock.getCounts24Hour() + "次" +
                ", 3天内异动=" + stock.getCounts3Day() + "次" +
                ", 1周内异动=" + stock.getCounts1Week() + "次";
    }

    public String formatStockListHtml(List<USStockMsg> stocks) {
        return stocks.stream()
                .map(this::formatStockInfoHtml)
                .collect(Collectors.joining("\n\n------\n\n")); // 用分隔线分隔
    }

    @Override
    public void sendMessage(List<USStockMsg> msgList) throws Exception {

        // 每个telegram的消息长度有限，此处进行分割
        List<List<USStockMsg>> result = TelegramMessageSplitter.splitList(msgList);
        for (List<USStockMsg> group : result) {
            String singleListStr = "";

            singleListStr = formatStockListHtml(group);

            sendMessage(singleListStr);
        }
    }

    @Override
    public void sendMessage(String text) {

        System.out.println(text);

        Map<String, String> request = new HashMap<>();
        request.put("chat_id", CHAT_ID);
        request.put("text", text);
        // 这里格式要转换一下，转换的更好看一些
        request.put("parse_mode", "HTML"); // 也可以用 "HTML"

        try {
            String response = restTemplate.postForObject(API_URL, request, String.class);
            System.out.println("Telegram API 响应: " + response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
