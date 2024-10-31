package cc.oofo.listener;

import cc.oofo.bot.sdk.message.Message;
import cc.oofo.bot.sdk.websocket.EventListener;
import cc.oofo.bot.sdk.websocket.MsgSourceType;
import cc.oofo.bot.sdk.websocket.Plugin;
import org.springframework.stereotype.Component;

/**
 * @author Sir丶雨轩
 * @since 2024/10/31
 */
@Component
public class BotListener implements EventListener {


    @Override
    public void onEvent(MsgSourceType sourceType, Message message) {
        for (Plugin plugin : plugins) {
            // 消息来源类型不匹配
            if(!plugin.info().getMsgSourceTypes().contains(sourceType)){
                continue;
            }
            // 匹配插件
            if(plugin.isMatch(message.getContent().trim())){
                plugin.onMessage(message);
            }
        }
    }
}
