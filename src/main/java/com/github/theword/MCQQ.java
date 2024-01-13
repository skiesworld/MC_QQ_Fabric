package com.github.theword;

import com.mojang.logging.LogUtils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;


public class MCQQ implements ModInitializer {

    static final Logger LOGGER = LogUtils.getLogger();

    static WsClient wsClient;
    static Map<String, String> httpHeaders = new HashMap<>();
    static int connectTime;
    static boolean serverOpen;

    static MinecraftServer minecraftServer;

    @Override
    public void onInitialize() {
        LOGGER.info("正在读取配置文件...");
        httpHeaders.put("x-self-name", Utils.unicodeEncode(ConfigReader.config().get("server_name").toString()));
        connectTime = 0;
        serverOpen = true;

        EventListener.eventRegister();

        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            LOGGER.info("WebSocket Client 正在启动...");
            LOGGER.info("WebSocket URL: " + ConfigReader.config().get("websocket_url"));
            minecraftServer = server;
            try {
                wsClient = new WsClient();
                wsClient.connect();
            } catch (Exception e) {
                LOGGER.error("WebSocket 连接失败，URL 格式错误。");
            }
        });
        ServerLifecycleEvents.SERVER_STOPPING.register(server -> {
            LOGGER.info("WebSocket Client 正在关闭...");
            serverOpen = false;
            wsClient.close();
        });
    }
}
