package com.github.theword;

import com.github.theword.event.*;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Objects;

import static com.github.theword.ConfigReader.configMap;
import static com.github.theword.MCQQ.wsClient;
import static com.github.theword.Utils.getEventJson;
import static com.github.theword.Utils.getFabricPlayer;

public class EventListener {

    public static void eventRegister() {
        ServerMessageEvents.CHAT_MESSAGE.register((message, player, params) -> {
            FabricServerMessageEvent fabricServerMessageEvent = new FabricServerMessageEvent(
                    "",
                    getFabricPlayer(player),
                    message.getContent().getString()
            );
            wsClient.sendMessage(getEventJson(fabricServerMessageEvent));

        });

        // TODO: 2024/1/13 无法触发
        ServerMessageEvents.COMMAND_MESSAGE.register((message, source, params) -> {
            if (source.isExecutedByPlayer() && (Boolean) configMap.get("command_message")) {
                FabricServerCommandMessageEvent fabricServerCommandMessageEvent = new FabricServerCommandMessageEvent(
                        "",
                        getFabricPlayer(Objects.requireNonNull(source.getPlayer())),
                        message.getContent().getString()
                );
                wsClient.sendMessage(getEventJson(fabricServerCommandMessageEvent));
            }
        });
        ServerLivingEntityEvents.AFTER_DEATH.register((entity, source) -> {
            if (entity.isPlayer() && (Boolean) configMap.get("death_message")) {
                ServerPlayerEntity player = (ServerPlayerEntity) entity;
                FabricServerLivingEntityAfterDeathEvent fabricServerLivingEntityAfterDeathEvent = new FabricServerLivingEntityAfterDeathEvent(
                        "",
                        getFabricPlayer(player),
                        source.getDeathMessage(player).getString()
                );
                wsClient.sendMessage(getEventJson(fabricServerLivingEntityAfterDeathEvent));
            }
        });

        ServerPlayConnectionEvents.JOIN.register((handler, player, server) -> {
            if ((Boolean) configMap.get("join_quit")) {
                FabricServerPlayConnectionJoinEvent fabricServerPlayConnectionJoinEvent = new FabricServerPlayConnectionJoinEvent(
                        getFabricPlayer(handler.player)
                );
                wsClient.sendMessage(getEventJson(fabricServerPlayConnectionJoinEvent));
            }
        });
        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
            if ((Boolean) configMap.get("join_quit")) {
                FabricServerPlayConnectionDisconnectEvent fabricServerPlayConnectionDisconnectEvent = new FabricServerPlayConnectionDisconnectEvent(
                        getFabricPlayer(handler.player)
                );
                wsClient.sendMessage(getEventJson(fabricServerPlayConnectionDisconnectEvent));
            }
        });
    }
}