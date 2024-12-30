# QQ 机器人 Java SDK

欢迎使用 QQ 机器人 Java SDK！本 SDK 提供了与 QQ 机器人官方 API 交互的便捷方式，帮助开发者快速开发和部署 QQ 机器人应用。
使用QQ官方机器人 [官方网站](https://q.qq.com/#/)
## 功能特性
- **便捷的 API 调用封装**：简化 HTTP 请求和参数处理。
- **事件监听**：轻松接收和处理来自 QQ 机器人的事件。
- **异步支持**：提供同步和异步两种方式调用 API。
- **自定义扩展**：允许用户根据需求自定义消息和事件处理逻辑。

## 安装

### 1. 通过 Maven 引入
在你的 `pom.xml` 文件中添加以下依赖：
```xml
<!-- QQ机器人SDK Java版 -->
<dependency>
    <groupId>cc.oofo</groupId>
    <artifactId>bot-sdk</artifactId>
    <version>${bot.version}</version>
</dependency>
```


## 快速开始

- 参考`bot-biz`模块


## 配置说明
```yml
app:
  sandbox: true
  open-api-path: "https://bots.qq.com/app"
  api-path: "https://api.sgroup.qq.com"
  api-path-sandbox: "https://sandbox.api.sgroup.qq.com"
  # 机器人相关配置
  bot:
    id: "####"
    qq: "####"
    token: "####"
    secret: "####"
```

## 贡献指南
欢迎贡献代码和提出建议！请提交 Pull Request 或 Issue。

## 许可证
本项目遵循 MIT 许可证。

---
**作者**：Yuxuan  
**GitHub**：[https://github.com/SirYuxuan/qq-bot-sdk](https://github.com/SirYuxuan/qq-bot-sdk)

