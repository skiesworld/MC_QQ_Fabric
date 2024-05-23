package com.github.theword.mcqq;

import com.github.theword.mcqq.commands.CommandExecutor;
import com.github.theword.mcqq.constant.WebsocketConstantMessage;
import com.github.theword.mcqq.handleMessage.HandleApiService;
import com.github.theword.mcqq.handleMessage.HandleCommandReturnMessageService;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;

import static com.github.theword.mcqq.utils.Tool.initTool;
import static com.github.theword.mcqq.utils.Tool.websocketManager;


public class MCQQ implements ModInitializer {

    public static MinecraftServer minecraftServer;

    @Override
    public void onInitialize() {
        initTool(true, new HandleApiService(), new HandleCommandReturnMessageService());
        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            minecraftServer = server;
            websocketManager.startWebsocket(null);
        });

        new EventProcessor();
        new CommandExecutor();

        ServerLifecycleEvents.SERVER_STOPPING.register(server -> websocketManager.stopWebsocket(1000, WebsocketConstantMessage.Client.CLOSING, null));
    }
}
