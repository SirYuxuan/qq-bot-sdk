package cc.oofo.bot.sdk.client;

import cc.oofo.bot.sdk.client.message.ReqMessage;
import cc.oofo.bot.sdk.config.AppConfig;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Sir丶雨轩
 * @since 2024/10/31
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class BotClient {

    private final AppConfig appConfig;
    private final TokenManager tokenManager;

    /**
     * 创建一个Post请求携带token
     * @param url 请求地址
     * @return HttpRequest
     */
    private HttpRequest createPost(String url){
        return HttpUtil.createPost(url).header("Authorization", "QQBot " + tokenManager.getToken());
    }


    /**
     * 发送群消息
     * @param groupOpenId 群OpenId
     * @param reqMessage 消息体
     */
    public void sendGroupMessage(String groupOpenId, ReqMessage reqMessage) {
        HttpRequest request = createPost(appConfig.getApiPath() + "/v2/groups/%s/messages".formatted(groupOpenId));
        request.body(reqMessage.toString());
        String body = request.execute().body();
        log.info("sendGroupMessage response: {}", body);
    }
}
