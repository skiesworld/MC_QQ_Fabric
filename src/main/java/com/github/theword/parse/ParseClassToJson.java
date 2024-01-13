package com.github.theword.parse;

import com.github.theword.event.*;
import com.google.gson.Gson;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.network.message.MessageType;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Objects;


public class ParseClassToJson {
    public static String parseChatMessageEvent(SignedMessage message, ServerPlayerEntity sender, MessageType.Parameters params) {
        Gson gson = new Gson();
        FabricServerMessageEvent fabricServerMessageEvent = new FabricServerMessageEvent(
                "",
                getFabricPlayer(sender),
                message.getContent().getString()
        );
        return gson.toJson(fabricServerMessageEvent);
    }

    public static String parseCommandMessageEvent(SignedMessage message, ServerCommandSource source, MessageType.Parameters params) {
        Gson gson = new Gson();

        FabricServerCommandMessageEvent fabricServerCommandMessageEvent = new FabricServerCommandMessageEvent(
                "",
                getFabricPlayer(Objects.requireNonNull(source.getPlayer())),
                message.getContent().getString()
        );
        return gson.toJson(fabricServerCommandMessageEvent);
    }

    public static String parseAfterDeathMessageEvent(ServerPlayerEntity player, DamageSource damageSource) {
        Gson gson = new Gson();
        FabricServerLivingEntityAfterDeathEvent fabricServerLivingEntityAfterDeathEvent = new FabricServerLivingEntityAfterDeathEvent(
                "",
                getFabricPlayer(player),
                damageSource.getDeathMessage(player).getString()
        );
        return gson.toJson(fabricServerLivingEntityAfterDeathEvent);
    }

    public static String parsePlayerConnectionJoinEvent(ServerPlayNetworkHandler handler, PacketSender sender, MinecraftServer server) {
        Gson gson = new Gson();
        FabricServerPlayConnectionJoinEvent fabricServerPlayConnectionJoinEvent = new FabricServerPlayConnectionJoinEvent(
                getFabricPlayer(handler.player)
        );
        return gson.toJson(fabricServerPlayConnectionJoinEvent);
    }

    public static String parsePlayerConnectionDisconnectEvent(ServerPlayNetworkHandler handler, MinecraftServer server) {
        Gson gson = new Gson();
        FabricServerPlayConnectionDisconnectEvent fabricServerPlayConnectionDisconnectEvent = new FabricServerPlayConnectionDisconnectEvent(
                getFabricPlayer(handler.player)
        );
        return gson.toJson(fabricServerPlayConnectionDisconnectEvent);
    }

    private static FabricServerPlayer getFabricPlayer(ServerPlayerEntity player) {
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
