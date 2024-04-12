package com.github.theword.mcqq;

import com.github.theword.mcqq.eventModels.fabric.*;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Objects;

import static com.github.theword.mcqq.utils.Tool.config;
import static com.github.theword.mcqq.utils.Tool.sendMessage;

public class EventProcessor {

    public EventProcessor() {
        if (config.isEnableChatMessage()) {
            ServerMessageEvents.CHAT_MESSAGE.register((message, player, params) -> {
                FabricServerMessageEvent fabricServerMessageEvent = new FabricServerMessageEvent(
                        "",
                        getFabricPlayer(player),
                        message.getContent().getString()
                );
                sendMessage(fabricServerMessageEvent);
            });
        }

        ServerMessageEvents.COMMAND_MESSAGE.register((message, source, params) -> {
            if (source.isExecutedByPlayer() && config.isEnableCommandMessage()) {

                String commandStr = message.getContent().getString();
                if (!(commandStr.startsWith("l ") || commandStr.startsWith("login ") || commandStr.startsWith("register ") || commandStr.startsWith("reg ") || commandStr.startsWith("mcqq "))) {
                    FabricServerCommandMessageEvent fabricServerCommandMessageEvent = new FabricServerCommandMessageEvent(
                            "",
                            getFabricPlayer(Objects.requireNonNull(source.getPlayer())),
                            message.getContent().getString()
                    );
                    sendMessage(fabricServerCommandMessageEvent);
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
                sendMessage(fabricServerLivingEntityAfterDeathEvent);
            }
        });

        ServerPlayConnectionEvents.JOIN.register((handler, player, server) -> {
            if (config.isEnableJoinMessage()) {
                FabricServerPlayConnectionJoinEvent fabricServerPlayConnectionJoinEvent = new FabricServerPlayConnectionJoinEvent(
                        getFabricPlayer(handler.player)
                );
                sendMessage(fabricServerPlayConnectionJoinEvent);
            }
        });
        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
            if (config.isEnableQuitMessage()) {
                FabricServerPlayConnectionDisconnectEvent fabricServerPlayConnectionDisconnectEvent = new FabricServerPlayConnectionDisconnectEvent(
                        getFabricPlayer(handler.player)
                );
                sendMessage(fabricServerPlayConnectionDisconnectEvent);
            }
        });
    }

    FabricServerPlayer getFabricPlayer(ServerPlayerEntity player) {
        FabricServerPlayer fabricServerPlayer = new FabricServerPlayer();

        fabricServerPlayer.setNickname(player.getName().getString());
        fabricServerPlayer.setUuid(player.getUuidAsString());
        fabricServerPlayer.setIp(player.getIp());
        fabricServerPlayer.setDisplayName(player.getDisplayName().getString());
        fabricServerPlayer.setMovementSpeed(player.getMovementSpeed());

        fabricServerPlayer.setBlockX(player.getBlockX());
        fabricServerPlayer.setBlockY(player.getBlockY());
        fabricServerPlayer.setBlockZ(player.getBlockZ());

        player.isCreative();
        player.isSpectator();
        player.isSneaking();
        player.isSleeping();
        player.isClimbing();
        player.isSwimming();

        return fabricServerPlayer;
    }
}