package com.github.theword.utils;

import com.github.theword.constant.WebsocketConstantMessage;
import com.github.theword.models.FabricEvent;
import com.github.theword.models.FabricServerPlayer;
import com.google.gson.Gson;
import net.minecraft.server.network.ServerPlayerEntity;

import static com.github.theword.MCQQ.*;

public class Tool {

    /**
     * 字符串转为 unicode 编码
     *
     * @param string 字符串
     * @return unicode编码
     */
    public static String unicodeEncode(String string) {
        char[] utfBytes = string.toCharArray();
        StringBuilder unicodeBytes = new StringBuilder();
        for (char utfByte : utfBytes) {
            String hexB = Integer.toHexString(utfByte);
            if (hexB.length() <= 2) {
                hexB = "00" + hexB;
            }
            unicodeBytes.append("\\u").append(hexB);
        }
        return unicodeBytes.toString();
    }

    /**
     * 获取事件的 json 字符串
     *
     * @param event 事件
     * @return json 字符串
     */
    public static String getEventJson(FabricEvent event) {
        Gson gson = new Gson();
        return gson.toJson(event);
    }

    public static FabricServerPlayer getFabricPlayer(ServerPlayerEntity player) {
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


    public static void sendMessage(String message) {
        if (config.isEnableMcQQ()) {
            wsClientList.forEach(
                    wsClient -> {
                        if (wsClient.isOpen()) {
                            wsClient.send(message);
                        } else {
                            LOGGER.info(String.format(WebsocketConstantMessage.WEBSOCKET_IS_NOT_OPEN_WHEN_SEND_MESSAGE, wsClient.getURI()));
                        }
                    }
            );
        }
    }
}
