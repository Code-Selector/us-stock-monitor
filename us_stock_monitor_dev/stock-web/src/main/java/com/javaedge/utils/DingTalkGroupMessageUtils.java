package com.javaedge.utils;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import com.taobao.api.ApiException;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @ClassName DingTalkGroupMessageUtils
 * @Author JavaEdge
 * @Version 1.0
 * @Description DingTalkGroupMessageUtils - 从环境变量读取密钥，避免硬编码
 **/
public class DingTalkGroupMessageUtils {

    // 从环境变量读取密钥，避免硬编码到代码中
    public static final String CUSTOM_ROBOT_TOKEN = 
        System.getenv("US_STOCK_DINGTALK_TOKEN");

    public static final String USER_ID = 
        System.getenv("US_STOCK_DINGTALK_USER_ID");

    public static final String SECRET = 
        System.getenv("US_STOCK_DINGTALK_SECRET");

    // 静态代码块验证密钥是否已配置
    static {
        if (CUSTOM_ROBOT_TOKEN == null || CUSTOM_ROBOT_TOKEN.isEmpty()) {
            throw new IllegalStateException("环境变量 US_STOCK_DINGTALK_TOKEN 未配置");
        }
        if (SECRET == null || SECRET.isEmpty()) {
            throw new IllegalStateException("环境变量 US_STOCK_DINGTALK_SECRET 未配置");
        }
    }

    public static void sendTextMessage() {
        try {
            Long timestamp = System.currentTimeMillis();
            String secret = SECRET;
            String stringToSign = timestamp + "\n" + secret;
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
            byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
            String sign = URLEncoder.encode(new String(Base64.encodeBase64(signData)), "UTF-8");

            // sign字段和timestamp字段必须拼接到请求URL上，否则会出现 310000 的错误信息
            String webhookUrl = "https://oapi.dingtalk.com/robot/send?sign=" + sign + "&timestamp=" + timestamp;
            DingTalkClient client = new DefaultDingTalkClient(webhookUrl);
            
            OapiRobotSendRequest req = new OapiRobotSendRequest();
            
            // 定义文本内容
            OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
            text.setContent("hello JavaEdge，这是java代码调用的钉钉机器人所发送的消息~~~");
            
            // 定义 @ 对象
            OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
            if (USER_ID != null && !USER_ID.isEmpty() && !USER_ID.contains("<you need")) {
                at.setAtUserIds(java.util.Arrays.asList(USER_ID));
            }
            
            // 设置消息类型
            req.setMsgtype("text");
            req.setText(text);
            req.setAt(at);
            
            OapiRobotSendResponse rsp = client.execute(req, CUSTOM_ROBOT_TOKEN);
            System.out.println(rsp.getBody());
        } catch (ApiException e) {
            throw new RuntimeException("钉钉机器人发送失败", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("编码错误", e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("加密算法不可用", e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException("密钥无效", e);
        }
    }

//    public static void main(String[] args) {
//        // 确保环境变量已设置: export US_STOCK_DINGTALK_TOKEN="xxx" && export US_STOCK_DINGTALK_SECRET="xxx"
//        new DingTalkGroupMessageUtils().sendTextMessage();
//    }
}
