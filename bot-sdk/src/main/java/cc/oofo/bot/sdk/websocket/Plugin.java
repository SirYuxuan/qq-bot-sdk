package cc.oofo.bot.sdk.websocket;

import cc.oofo.bot.sdk.message.Message;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 插件机制
 *
 * @author Sir丶雨轩
 * @since 2024/10/31
 */
public interface Plugin {


    /**
     * 获取插件信息
     *
     * @return 插件信息
     */
    PluginInfo info();

    /**
     * 是否匹配 匹配了才会调用 {@link #onMessage(Message)}
     *
     * @param msgContent 消息内容
     * @return 是否匹配
     */
    boolean isMatch(String msgContent);

    /**
     * 处理消息
     *
     * @param message 消息内容
     */
    void onMessage(Message message);

    @Data
    @AllArgsConstructor
    class PluginInfo {
        /**
         * 插件名称
         */
        private String name;
        /**
         * 插件描述
         */
        private String description;

        private List<MsgSourceType> msgSourceTypes;
    }
}
