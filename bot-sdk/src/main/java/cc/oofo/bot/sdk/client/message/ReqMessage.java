package cc.oofo.bot.sdk.client.message;

import cc.oofo.bot.sdk.message.Message;
import cc.oofo.bot.sdk.websocket.MsgSourceType;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.SerializeConfig;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 请求消息对象
 *
 * @author Sir丶雨轩
 * @since 2024/10/31
 */
@Data
@Accessors(chain = true)
public class ReqMessage {

    /**
     * 消息内容
     */
    private String content;
    /**
     * 消息类型： 0 文本，2 是 markdown，3 ark 消息，4 embed，7 media 富媒体
     * {@link cc.oofo.bot.sdk.client.message.ReqMessageType}
     */
    private int msgType;
    /**
     * Markdown对象
     * {@link cc.oofo.bot.sdk.client.message.Markdown}
     */
    private Markdown markdown;
    /**
     * Keyboard对象
     * {@link cc.oofo.bot.sdk.client.message.Keyboard}
     */
    private Keyboard keyboard;
    /**
     * Media对象
     * {@link cc.oofo.bot.sdk.client.message.Media}
     */
    private Media media;
    /**
     * Ark对象
     * {@link cc.oofo.bot.sdk.client.message.Ark}
     */
    private Ark ark;
    /**
     * 暂未启用
     */
    private Object messageReference;
    /**
     * 前置收到的事件 ID，用于发送被动消息，支持事件："INTERACTION_CREATE"、"GROUP_ADD_ROBOT"、"GROUP_MSG_RECEIVE"
     */
    private String eventId;
    /**
     * 前置收到的用户发送过来的消息 ID，用于发送被动消息（回复）
     */
    private String msgId;
    /**
     * 回复消息的序号，与 msg_id 联合使用，避免相同消息id回复重复发送，不填默认是 1。相同的 msg_id + msg_seq 重复发送会失败。
     */
    private String msgSeq;

    /**
     * 序列化配置 - 驼峰转下划线
     */
    private final SerializeConfig serializeConfig = new SerializeConfig() {{
        propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
    }};

    @Override
    public String toString() {
        return JSON.toJSONString(this, serializeConfig);
    }

    /**
     * 创建文本消息
     *
     * @param text 文本内容
     * @return ReqMessage
     */
    public static ReqMessage ofText(String text) {
        ReqMessage message = new ReqMessage();
        message.setContent(text);
        message.setMsgType(ReqMessageType.TEXT.getVal());
        return message;
    }

    /**
     * 设置来源消息ID
     * @param sourceType 来源类型
     * @param message 消息
     * @return ReqMessage
     */
    public ReqMessage ofSource(MsgSourceType sourceType,Message message){
        this.setMsgId(message.getId());
        this.setEventId(sourceType.getText());
        return this;
    }

}
