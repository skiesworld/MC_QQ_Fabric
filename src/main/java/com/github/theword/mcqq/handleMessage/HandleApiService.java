package com.github.theword.mcqq.handleMessage;

import com.github.theword.mcqq.returnBody.returnModle.MyBaseComponent;
import com.github.theword.mcqq.returnBody.returnModle.MyTextComponent;
import com.github.theword.mcqq.returnBody.returnModle.SendTitle;
import com.github.theword.mcqq.utils.ParseJsonToEvent;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.network.packet.s2c.play.SubtitleS2CPacket;
import net.minecraft.network.packet.s2c.play.TitleFadeS2CPacket;
import net.minecraft.network.packet.s2c.play.TitleS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import org.java_websocket.WebSocket;

import java.util.List;

import static com.github.theword.mcqq.MCQQ.minecraftServer;

public class HandleApiService implements HandleApi {
    private final ParseJsonToEvent parseJsonToEvent = new ParseJsonToEvent();

    /**
     * 广播消息
     *
     * @param webSocket   websocket
     * @param messageList 消息体
     */
    @Override
    public void handleBroadcastMessage(WebSocket webSocket, List<MyTextComponent> messageList) {
        MutableText result = parseJsonToEvent.parseMessages(messageList);
        for (ServerPlayerEntity serverPlayer : minecraftServer.getPlayerManager().getPlayerList()) {
            serverPlayer.sendMessage(result);
        }
    }

    /**
     * 广播 Send Title 消息
     *
     * @param webSocket websocket
     * @param sendTitle Send Title 消息体
     */
    @Override
    public void handleSendTitleMessage(WebSocket webSocket, SendTitle sendTitle) {
        sendPacket(new TitleS2CPacket(parseJsonToEvent.parseMessages(sendTitle.getTitle())));
        if (sendTitle.getSubtitle() != null)
            sendPacket(new SubtitleS2CPacket(parseJsonToEvent.parseMessages(sendTitle.getSubtitle())));
        sendPacket(new TitleFadeS2CPacket(sendTitle.getFadein(), sendTitle.getStay(), sendTitle.getFadeout()));
    }

    /**
     * 广播 Action Bar 消息
     *
     * @param webSocket   websocket
     * @param messageList Action Bar 消息体
     */
    @Override
    public void handleActionBarMessage(WebSocket webSocket, List<MyBaseComponent> messageList) {
        sendPacket(new GameMessageS2CPacket(parseJsonToEvent.parseMessages(messageList), true));
    }

    private void sendPacket(Packet<?> packet) {
        for (ServerPlayerEntity player : minecraftServer.getPlayerManager().getPlayerList()) {
            player.networkHandler.sendPacket(packet);
        }
    }
}
