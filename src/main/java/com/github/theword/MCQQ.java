package com.github.theword;

import com.mojang.logging.LogUtils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;
import org.slf4j.Logger;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


public class MCQQ implements ModInitializer {

    static final Logger LOGGER = LogUtils.getLogger();
    public static Config config = new Config(true);
    static MinecraftServer minecraftServer;
    static List<WsClient> wsClientList = new ArrayList<>();

    @Override
    public void onInitialize() {
        ServerLifecycleEvents.SERVER_STARTING.register(server -> {

            LOGGER.info("[MC_QQ] WebSocket Client 正在启动...");
            minecraftServer = server;

            config.getWebsocketUrlList().forEach(url -> {
                try {
                    WsClient wsClient = new WsClient(url);
                    wsClient.connect();
                    wsClientList.add(wsClient);
                } catch (URISyntaxException e) {
                    LOGGER.warn("[MC_QQ]|连接至：%s WebSocket URL 配置错误，无法连接！".formatted(url));
                }
            });
        });

        EventListener.eventRegister();
        CommandRegister.commandRegister();

        ServerLifecycleEvents.SERVER_STOPPING.register(server -> wsClientList.forEach(
                wsClient -> wsClient.close(
                        1000,
                        "[MC_QQ]|连接至：%s 的 WebSocket Client 正在关闭".formatted(wsClient.getURI())
                )
        ));
    }
}
