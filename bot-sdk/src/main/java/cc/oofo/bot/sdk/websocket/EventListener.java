package cc.oofo.bot.sdk.websocket;

import cc.oofo.bot.sdk.message.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sir丶雨轩
 * @since 2024/10/31
 */
public interface EventListener {

    /**
     * 已加载的插件
     */
    List<Plugin> plugins = new ArrayList<>();

    /**
     * 获取已加载的插件
     * @return 已加载的插件
     */
    default List<Plugin> loadedPlugins() {
        return plugins;
    }

    /**
     * 事件处理
     * @param sourceType 消息来源
     * @param message 消息内容
     */
    void onEvent(MsgSourceType sourceType, Message message);
}
