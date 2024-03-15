package com.github.theword;

import com.github.theword.event.*;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Objects;

import static com.github.theword.MCQQ.config;
import static com.github.theword.Utils.*;

public class EventListener {

    public EventListener() {
        if (config.isEnableChatMessage()) {
            ServerMessageEvents.CHAT_MESSAGE.register((message, player, params) -> {
                FabricServerMessageEvent fabricServerMessageEvent = new FabricServerMessageEvent(
                        "",
                        getFabricPlayer(player),
                        message.getContent().getString()
                );
                sendMessage(getEventJson(fabricServerMessageEvent));
            });
        }

        ServerMessageEvents.COMMAND_MESSAGE.register((message, source, params) -> {
            if (source.isExecutedByPlayer() && config.isEnableCommandMessage()) {

                String commandStr = message.getContent().getString();
                if (!(commandStr.startsWith("l ") || commandStr.startsWith("login ") || commandStr.startsWith("register ") || commandStr.startsWith("reg "))) {
                    FabricServerCommandMessageEvent fabricServerCommandMessageEvent = new FabricServerCommandMessageEvent(
                            "",
                            getFabricPlayer(Objects.requireNonNull(source.getPlayer())),
                            message.getContent().getString()
                    );
                    sendMessage(getEventJson(fabricServerCommandMessageEvent));
                }
            }
        });
        ServerLivingEntityEvents.AFTER_DEATH.register((entity, source) -> {
            if (entity.isPlayer() && config.isEnableDeathMessage()) {
                ServerPlayerEntity player = (ServerPlayerEntity) entity;
                FabricServerLivingEntityAfterDeathEvent fabricServerLivingEntityAfterDeathEvent = new FabricServerLivingEntityAfterDeathEvent(
                        "",
                        getFabricPlayer(player),
                        source.getDeathMessage(player).getString()
                );
                sendMessage(getEventJson(fabricServerLivingEntityAfterDeathEvent));
            }
        });

        ServerPlayConnectionEvents.JOIN.register((handler, player, server) -> {
            if (config.isEnableJoinMessage()) {
                FabricServerPlayConnectionJoinEvent fabricServerPlayConnectionJoinEvent = new FabricServerPlayConnectionJoinEvent(
                        getFabricPlayer(handler.player)
                );
                sendMessage(getEventJson(fabricServerPlayConnectionJoinEvent));
            }
        });
        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
            if (config.isEnableQuitMessage()) {
                FabricServerPlayConnectionDisconnectEvent fabricServerPlayConnectionDisconnectEvent = new FabricServerPlayConnectionDisconnectEvent(
                        getFabricPlayer(handler.player)
                );
                sendMessage(getEventJson(fabricServerPlayConnectionDisconnectEvent));
            }
        });
    }
}