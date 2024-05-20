package com.github.theword.mcqq;

import com.github.theword.mcqq.commands.CommandRegister;
import com.github.theword.mcqq.constant.WebsocketConstantMessage;
import com.github.theword.mcqq.utils.HandleWebsocketMessageService;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;

import static com.github.theword.mcqq.utils.Tool.initTool;
import static com.github.theword.mcqq.utils.WebsocketManager.startWebsocket;
import static com.github.theword.mcqq.utils.WebsocketManager.stopWebsocket;


public class MCQQ implements ModInitializer {

    public static MinecraftServer minecraftServer;

    @Override
    public void onInitialize() {
        initTool("", true, new HandleWebsocketMessageService());
        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            minecraftServer = server;
            startWebsocket(null);
        });

        new EventProcessor();
        new CommandRegister();

        ServerLifecycleEvents.SERVER_STOPPING.register(server -> stopWebsocket(1000, WebsocketConstantMessage.WEBSOCKET_CLOSING));
    }
}
