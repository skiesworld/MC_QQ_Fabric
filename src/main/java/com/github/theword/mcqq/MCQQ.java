package com.github.theword.mcqq;

import com.github.theword.mcqq.commands.CommandRegister;
import com.github.theword.mcqq.constant.WebsocketConstantMessage;
import com.github.theword.mcqq.utils.Config;
import com.github.theword.mcqq.utils.HandleWebsocketMessageService;
import com.github.theword.mcqq.websocket.WsClient;
import com.mojang.logging.LogUtils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import static com.github.theword.mcqq.utils.Tool.*;


public class MCQQ implements ModInitializer {

    public static MinecraftServer minecraftServer;

    @Override
    public void onInitialize() {
        logger = LogUtils.getLogger();
        config = new Config(true);
        wsClientList = new ArrayList<>();
        handleWebsocketMessage = new HandleWebsocketMessageService();
        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            logger.info(WebsocketConstantMessage.WEBSOCKET_RUNNING);
            minecraftServer = server;
            config.getWebsocketUrlList().forEach(websocketUrl -> {
                try {
                    WsClient wsClient = new WsClient(new URI(websocketUrl));
                    wsClient.connect();
                    wsClientList.add(wsClient);
                } catch (URISyntaxException e) {
                    logger.warn(WebsocketConstantMessage.WEBSOCKET_ERROR_URI_SYNTAX_ERROR.formatted(websocketUrl));
                }
            });
        });

        new EventProcessor();
        new CommandRegister();

        ServerLifecycleEvents.SERVER_STOPPING.register(server -> wsClientList.forEach(
                wsClient -> wsClient.stopWithoutReconnect(
                        1000,
                        WebsocketConstantMessage.WEBSOCKET_CLOSING.formatted(wsClient.getURI())
                )
        ));
    }
}
