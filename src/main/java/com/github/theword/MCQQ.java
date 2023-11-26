package com.github.theword;

import com.mojang.logging.LogUtils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;

import static com.github.theword.ConfigReader.config;

public class MCQQ implements ModInitializer {

    static final Logger LOGGER = LogUtils.getLogger();

    public static String serverName;
    static WsClient wsClient;
    static Map<String, String> httpHeaders = new HashMap<>();
    static int connectTime;
    static boolean serverOpen;

    @Override
    public void onInitialize() {
        LOGGER.info("正在读取配置文件...");
        httpHeaders.put("x-self-name", Utils.unicodeEncode(ConfigReader.config().get("server_name").toString()));
        connectTime = 0;
        serverOpen = true;
        serverName = config().get("server_name").toString();
        EventListener.eventRegister();

        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            LOGGER.info("WebSocket Client 正在启动...");
            LOGGER.info("WebSocket URL: " + ConfigReader.config().get("websocket_url"));
            try {
                wsClient = new WsClient();
                wsClient.connectBlocking();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        ServerLifecycleEvents.SERVER_STOPPING.register(server -> {
            LOGGER.info("WebSocket Client 正在关闭...");
            serverOpen = false;
            wsClient.close();
        });
    }
}
