package cc.oofo.bot.sdk.websocket;

import cc.oofo.bot.sdk.client.TokenManager;
import cc.oofo.bot.sdk.config.AppConfig;
import cc.oofo.bot.sdk.data.BotPayload;
import cc.oofo.bot.sdk.data.OpEnum;
import cc.oofo.bot.sdk.message.Message;
import cc.oofo.bot.sdk.utils.CommonUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.tio.websocket.client.WebSocket;
import org.tio.websocket.client.WsClient;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Sir丶雨轩
 * @since 2024/10/7
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class BotWebSocket implements CommandLineRunner {

    private final RedisTemplate<String, Object> redisTemplate;
    // 事件监听器
    private final EventListener listener;
    //  存储 bot 的 s 值
    private final String botSKey = "bot:s_val";
    // 机器人配置
    private final AppConfig appConfig;
    // Token管理器
    private final TokenManager tokenManager;
    // 心跳发送周期
    private int heartbeatInterval = 0;
    // 当前机器人的会话ID
    private String sessionId = null;
    // WebSocket 客户端
    private WebSocket ws;
    // 定时任务 - 发送心跳包
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);


    /**
     * 获取 WsClient 对象
     *
     * @return WsClient 对象
     */
    @SneakyThrows
    private WsClient getWsClient() {
        String token = tokenManager.getToken();
        // 获取网关地址
        HttpRequest request = HttpUtil.createGet(appConfig.getApiPath() + "/gateway");
        request.header("Authorization", "QQBot " + token);
        String body = request.execute().body();
        return WsClient.create(CommonUtil.convertToPortedUrl(JSONUtil.parseObj(body).getStr("url"), 443));
    }


    /**
     * 启动事件监听，消息事件对外提供
     *
     * @param listener 事件监听器
     * @throws Exception 异常
     */
    public void startListening() throws Exception {

        WsClient client = getWsClient();
        ws = client.connect();
        ws.addOnMessage(e -> {
            // 解析消息
            BotPayload botPayload = JSONUtil.parseObj(e.data.getWsBodyText()).toBean(BotPayload.class);
            log.info("收到消息, OP:{}\tD:{}", OpEnum.fromCode(botPayload.getOp()), botPayload.getD());
            // 缓存 s 用来发送心跳包
            redisTemplate.opsForValue().set(botSKey, botPayload.getS());

            // Op = Hello
            if (botPayload.getOp() == OpEnum.HELLO.getCode()) {
                replyHello(botPayload);
                return;
            }
            // Op = Dispatch
            if (botPayload.getOp() == OpEnum.DISPATCH.getCode()) {
                // 鉴权成功，服务器发送 READY 消息
                if ("READY".equals(botPayload.getT())) {
                    sessionId = JSONUtil.parseObj(botPayload.getD()).getStr("session_id");
                    log.debug("机器人完成准备, session id: {}", sessionId);
                    startSendHeartbeat();
                    return;
                }

                MsgSourceType msgSourceType = null;
                if ("GROUP_AT_MESSAGE_CREATE".equals(botPayload.getT())) {
                    // 收到群里@的消息
                    msgSourceType = MsgSourceType.GROUP;
                }
                if ("C2C_MESSAGE_CREATE".equals(botPayload.getT())) {
                    // 收到私聊消息
                    msgSourceType = MsgSourceType.FRIEND;
                }
                if (msgSourceType != null) {
                    listener.onEvent(msgSourceType, JSONUtil.parseObj(botPayload.getD()).toBean(Message.class));
                }

            }

            // Op = HeartbeatAck 发送心跳包收到回复
            if (botPayload.getOp() == OpEnum.HEARTBEAT_ACK.getCode()) {
                log.debug("Received heartbeat ack");
            }
        });
    }

    /**
     * 发送心跳包
     */
    private void startSendHeartbeat() {
        scheduler.scheduleAtFixedRate(() -> {
            BotPayload heartbeatPayload = new BotPayload();
            heartbeatPayload.setOp(OpEnum.HEARTBEAT.getCode());
            heartbeatPayload.setD(redisTemplate.opsForValue().get(botSKey));
            log.debug("发送心跳包,{}", heartbeatPayload);
            ws.send(JSONUtil.toJsonStr(heartbeatPayload));
        }, 0, heartbeatInterval, TimeUnit.MILLISECONDS);
    }

    /**
     * 回应服务器的hello进行鉴权
     *
     * @param botPayload 服务器发送的hello消息
     */
    private void replyHello(BotPayload botPayload) {
        // 心跳发送周期
        heartbeatInterval = JSONUtil.parseObj(botPayload.getD()).getInt("heartbeat_interval");
        // 发送鉴权消息
        BotPayload authPayload = new BotPayload();
        authPayload.setOp(OpEnum.IDENTIFY.getCode());
        JSONObject d = JSONUtil.createObj()
                .set("token", "QQBot " + tokenManager.getToken())
                .set("intents", 1 << 25);
        authPayload.setD(d);
        ws.send(JSONUtil.toJsonStr(authPayload));
    }

    /**
     * 启动监听
     */
    @Override
    public void run(String... args) throws Exception {
        log.info("开始加载插件...");
        SpringUtil.getBeansOfType(Plugin.class).forEach((name,bean)->{
            log.info("插件加载完成:{}",bean.info().getName());
            if(bean.info().getMsgSourceTypes() != null){
                listener.loadedPlugins().add(bean);
            }else{
                log.error("插件【{}】加载异常，插件没有指定使用范围",bean.info().getName());
            }

        });
        log.info("插件加载完成, 插件数量:{}",listener.loadedPlugins().size());
        startListening();
    }
}
