# MC_QQ_Fabric

## 已实现事件

- 玩家聊天事件
- 玩家加入事件
- 玩家离开事件
- 玩家死亡事件
- 玩家命令事件（已过滤 `l `、`login `、`reg `、`register ` 命令且默认关闭）

## 其他

- 命令事件监听规则
  ```
  Called when the server broadcasts a command message to all players, such as one from /me and /say (but not ones that specify the recipients like /msg)
  
  当服务器向所有玩家广播命令消息时调用，例如来自/me和/say的消息（但不是指定接收方的消息，如/msg）
  ```