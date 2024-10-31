package cc.oofo.bot.sdk.data;

import lombok.Data;

/**
 * @author Sir丶雨轩
 * @since 2024/10/31
 */
@Data
public class BotPayload {

    private int op;
    private Object d;
    private String s;
    private String t;
}
