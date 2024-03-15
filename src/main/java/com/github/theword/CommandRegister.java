package com.github.theword;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.Text;

import static com.github.theword.MCQQ.config;
import static com.github.theword.MCQQ.wsClientList;
import static com.github.theword.Utils.connectWebsocket;

public class CommandRegister {
    public static void commandRegister() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
                dispatcher.register(CommandManager.literal("mcqq")
                        .requires(source -> source.hasPermissionLevel(2))
                        .then(CommandManager.literal("reload")
                                .executes(context -> {
                                            config = new Config(true);
                                            context.getSource().sendFeedback(() -> Text.literal("[MC_QQ] 配置文件已重载"), true);
                                            wsClientList.forEach(wsClient -> {
                                                if (wsClient.isOpen()) {
                                                    wsClient.close();
                                                }
                                                wsClientList.remove(wsClient);
                                            });
                                            context.getSource().sendFeedback(() -> Text.literal("[MC_QQ] 旧链接清理完成"), true);
                                            config.getWebsocketUrlList().forEach(websocketUrl -> {
                                                WsClient wsClient = connectWebsocket(websocketUrl);
                                                if (wsClient == null) {
                                                    context.getSource().sendFeedback(() -> Text.literal("[MC_QQ] %s 的连接为空，无法连接".formatted(websocketUrl)), true);
                                                } else {
                                                    wsClientList.add(wsClient);
                                                }
                                            });
                                            context.getSource().sendFeedback(() -> Text.literal("[MC_QQ] 重载完成"), true);
                                            return 1;
                                        }
                                )
                        )
                )
        );

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
                dispatcher.register(CommandManager.literal("mcqq")
                        .requires(source -> source.hasPermissionLevel(2))
                        .then(CommandManager.literal("reconnect")
                                .executes(context -> {
                                    wsClientList.forEach(wsClient -> {
                                        if (!wsClient.isOpen()) {
                                            wsClient.reconnectWebsocket();
                                            context.getSource().sendFeedback(() -> Text.literal("[MC_QQ] %s 的连接已重连".formatted(wsClient.getURI())), true);
                                        }
                                    });
                                    context.getSource().sendFeedback(() -> Text.literal("[MC_QQ] 已将未连接的 Websocket Client 重新连接"), true);
                                    return 1;
                                }).then(CommandManager.literal("all").executes(context -> {
                                    wsClientList.forEach(WsClient::reconnectWebsocket);
                                    context.getSource().sendFeedback(() -> Text.literal("[MC_QQ] 已将所有 Websocket Client 重新连接"), true);
                                    return 1;
                                }))
                        )
                )
        );

    }
}
