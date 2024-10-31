package cc.oofo.bot.sdk.utils;

/**
 * 通用工具类
 * @author Sir丶雨轩
 * @since 2024/10/31
 */
public final class CommonUtil {

    /**
     * 将原 URL 转换为带端口的 URL
     *
     * @param originalUrl 原 URL
     * @param port        端口
     * @return 带端口的 URL
     */
    public static String convertToPortedUrl(String originalUrl, int port) {
        // 去掉原 URL 的 "wss://" 前缀
        String withoutProtocol = originalUrl.replace("wss://", "");

        // 找到第一个斜杠的位置
        int slashIndex = withoutProtocol.indexOf('/');

        // 分离主机名和路径
        String host = withoutProtocol.substring(0, slashIndex);
        String path = withoutProtocol.substring(slashIndex);

        // 构建新的带端口的 URL
        return String.format("wss://%s:%d%s", host, port, path);
    }
}
