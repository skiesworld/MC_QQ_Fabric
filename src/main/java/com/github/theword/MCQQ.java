package com.github.theword;

import com.github.theword.commands.CommandRegister;
import com.github.theword.constant.WebsocketConstantMessage;
import com.mojang.logging.LogUtils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;
import org.slf4j.Logger;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


public class MCQQ implements ModInitializer {

    public static final Logger LOGGER = LogUtils.getLogger();
    public static Config config = new Config(true);
    public static MinecraftServer minecraftServer;
    public static List<WsClient> wsClientList = new ArrayList<>();

    @Override
    public void onInitialize() {
        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            LOGGER.info(WebsocketConstantMessage.WEBSOCKET_RUNNING);
            minecraftServer = server;
            config.getWebsocketUrlList().forEach(url -> {
                try {
                    WsClient wsClient = new WsClient(url);
                    wsClient.connect();
                    wsClientList.add(wsClient);
                } catch (URISyntaxException e) {
                    LOGGER.warn(WebsocketConstantMessage.WEBSOCKET_ERROR_URI_SYNTAX_ERROR.formatted(url));
                }
            });
        });

        new EventProcessor();
        new CommandRegister();

        ServerLifecycleEvents.SERVER_STOPPING.register(server -> wsClientList.forEach(
                wsClient -> {
                    wsClient.getTimer().cancel();
                    wsClient.close(
                            1000,
                            WebsocketConstantMessage.WEBSOCKET_CLOSING.formatted(wsClient.getURI())
                    );
                }
        ));
    }
}
