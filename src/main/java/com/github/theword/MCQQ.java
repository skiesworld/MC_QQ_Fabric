package com.github.theword;

import com.mojang.logging.LogUtils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;

import static com.github.theword.ConfigReader.configMap;
import static com.github.theword.ConfigReader.loadConfig;


public class MCQQ implements ModInitializer {

    static final Logger LOGGER = LogUtils.getLogger();

    static WsClient wsClient;
    static Map<String, String> httpHeaders = new HashMap<>();
    static int connectTime;
    static boolean serverOpen;

    static MinecraftServer minecraftServer;

    @Override
    public void onInitialize() {
        ServerLifecycleEvents.SERVER_STARTING.register(server -> {

            loadConfig();
            connectTime = 0;
            serverOpen = true;

            httpHeaders.put("x-self-name", Utils.unicodeEncode(configMap.get("server_name").toString()));

            LOGGER.info("[MC_QQ] WebSocket Client 正在启动...");
            LOGGER.info("[MC_QQ] WebSocket URL: " + configMap.get("websocket_url"));
            minecraftServer = server;
            try {
                wsClient = new WsClient();
                wsClient.connect();
            } catch (Exception e) {
                LOGGER.error("[MC_QQ] WebSocket 连接失败，URL 格式错误。");
            }
        });

        EventListener.eventRegister();

        ServerLifecycleEvents.SERVER_STOPPING.register(server -> {
            LOGGER.info("[MC_QQ] WebSocket Client 正在关闭...");
            serverOpen = false;
            wsClient.close();
        });
    }
}
