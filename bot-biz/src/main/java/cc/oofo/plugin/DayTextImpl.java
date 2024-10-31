package cc.oofo.plugin;

import cc.oofo.bot.sdk.client.BotClient;
import cc.oofo.bot.sdk.client.message.ReqMessage;
import cc.oofo.bot.sdk.message.Message;
import cc.oofo.bot.sdk.websocket.MsgSourceType;
import cc.oofo.bot.sdk.websocket.Plugin;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 每日一言
 *
 * @author Sir丶雨轩
 * @since 2024/10/31
 */
@Component
@RequiredArgsConstructor
public class DayTextImpl implements Plugin {

    private final BotClient client;

    @Override
    public PluginInfo info() {
        return new PluginInfo("每日一言", "每日一句励志的话", List.of(MsgSourceType.GROUP, MsgSourceType.FRIEND));
    }

    @Override
    public boolean isMatch(String msgContent) {
        return "/每日一言".equals(msgContent);
    }

    @Override
    public void onMessage(Message msgContent) {
        String body = HttpUtil.get("https://api.oofo.cc/text/oneDay");
        String sendData = "接口调用失败，请稍后重试...";
        JSONObject entries = JSONUtil.parseObj(body);
        if (entries.getInt("code") == 200) {
            sendData = entries.getStr("data");
        }
        client.sendGroupMessage(msgContent.getGroupOpenid(), ReqMessage.ofText(sendData).ofSource(MsgSourceType.GROUP, msgContent));

    }
}
