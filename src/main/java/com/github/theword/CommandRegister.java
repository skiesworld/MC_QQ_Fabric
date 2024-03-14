package com.github.theword;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.text.Text;

import java.net.URISyntaxException;

import static com.github.theword.ConfigReader.configMap;
import static com.github.theword.MCQQ.*;

public class CommandRegister {
    public static void commandRegister() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
                dispatcher.register(CommandManager.literal("mcqq")
                        .requires(source -> source.hasPermissionLevel(2)) // 只有管理员能够执行命令。命令不会对非操作员或低于 1 级权限的玩家显示在 tab-完成中，也不会让他们执行。
                        .then(CommandManager.argument("value", StringArgumentType.string())
                                .executes(
                                        context -> {
                                            final String value = StringArgumentType.getString(context, "value");
                                            if (value.isEmpty()) {
                                                context.getSource().sendFeedback(() -> Text.literal("未输入参数"), false);
                                                return 0;
                                            } else if (value.equals("reconnect")) {
                                                // TODO 命令重连
                                                if (wsClient.isOpen()) {
                                                    context.getSource().sendFeedback(() -> Text.literal("Ws Client 已连接，无需重新连接"), false);
                                                    return 0;
                                                }
                                                context.getSource().sendFeedback(() -> Text.literal("Ws Client 已关闭连接，将在3秒后重连..."), false);
                                                try {
                                                    Thread.sleep(3000);
                                                } catch (InterruptedException e) {
                                                    LOGGER.warn("[MC_QQ] 重连线程被打断");
                                                    context.getSource().sendFeedback(() -> Text.literal("重连线程被打断"), false);
                                                }
                                                // TODO 提取连接公用部分作为方法
                                                try {
                                                    wsClient = new WsClient();
                                                    httpHeaders.put("x-self-name", Utils.unicodeEncode(configMap.get("server_name").toString()));
                                                    wsClient.connect();
                                                } catch (URISyntaxException e) {
                                                    LOGGER.error("[MC_QQ] WebSocket 连接失败，URL 格式错误。");
                                                    context.getSource().sendFeedback(() -> Text.literal("Ws Client 连接失败"), false);
                                                }
                                                context.getSource().sendFeedback(() -> Text.literal("Ws Client 已重连"), false);
                                                return 1;
                                            } else {
                                                context.getSource().sendFeedback(() -> Text.literal("未知参数"), false);
                                                return 0;
                                            }
                                        }
                                ))
                )
        );
    }
}
