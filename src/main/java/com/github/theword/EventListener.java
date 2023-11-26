package com.github.theword;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.network.ServerPlayerEntity;

import static com.github.theword.ConfigReader.config;
import static com.github.theword.MCQQ.wsClient;
import static com.github.theword.parse.ParseClassToJson.*;

public class EventListener {

    public static void eventRegister() {
        ServerMessageEvents.CHAT_MESSAGE.register((message, player, params) -> {
            if ((Boolean) config().get("enable_mc_qq")) {
                wsClient.sendMessage(parseChatMessageEvent(message, player, params));
            }
        });
        ServerMessageEvents.COMMAND_MESSAGE.register((message, source, params) -> {
            if (source.isExecutedByPlayer() && (Boolean) config().get("command_message")) {
                wsClient.sendMessage(parseCommandMessageEvent(message, source, params));
            }
        });
        ServerLivingEntityEvents.AFTER_DEATH.register((entity, source) -> {
            if (entity.isPlayer() && (Boolean) config().get("death_message")) {
                ServerPlayerEntity player = (ServerPlayerEntity) entity;
                wsClient.sendMessage(parseAfterDeathMessageEvent(player, source));
            }
        });

        ServerPlayConnectionEvents.JOIN.register((handler, player, server) -> {
            if ((Boolean) config().get("join_quit")) {
                wsClient.sendMessage(parsePlayerConnectionJoinEvent(handler, player, server));
            }
        });
        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
            if ((Boolean) config().get("join_quit")) {
                wsClient.sendMessage(parsePlayerConnectionDisconnectEvent(handler, server));
            }
        });
    }
}