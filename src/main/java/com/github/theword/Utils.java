package com.github.theword;


import com.github.theword.returnBody.BaseReturnBody;
import com.github.theword.returnBody.MessageReturnBody;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import static com.github.theword.MCQQ.LOGGER;
import static com.github.theword.MCQQ.minecraftServer;
import static com.github.theword.parse.ParseJsonToEvent.parseMessageToText;

public class Utils {

    /**
     * 字符串转为 unicode 编码
     *
     * @param string 字符串
     * @return unicode编码
     */
    static String unicodeEncode(String string) {
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


    public static void parseWebSocketJson(String message) {
        // 组合消息
        Gson gson = new Gson();
        BaseReturnBody baseReturnBody = gson.fromJson(message, BaseReturnBody.class);
        JsonElement data = baseReturnBody.getData();
        switch (baseReturnBody.getApi()) {
            case "broadcast":
                MessageReturnBody messageList = gson.fromJson(data, MessageReturnBody.class);
                String result = parseMessageToText(messageList.getMessageList());
                for (ServerPlayerEntity serverPlayer : minecraftServer.getPlayerManager().getPlayerList()) {
                    serverPlayer.sendMessage(Text.literal(result));
                }
                break;
            default:
                LOGGER.warn("未知的 API: " + baseReturnBody.getApi());
                break;
        }
    }
}
