package com.github.theword.mcqq.handleMessage;

import com.github.theword.mcqq.returnBody.ActionbarReturnBody;
import com.github.theword.mcqq.returnBody.MessageReturnBody;
import com.github.theword.mcqq.returnBody.SendTitleReturnBody;
import com.github.theword.mcqq.utils.ParseJsonToEvent;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;

import static com.github.theword.mcqq.MCQQ.minecraftServer;
import static com.github.theword.mcqq.utils.Tool.logger;

public class HandleApiService implements HandleApi {
    private final ParseJsonToEvent parseJsonToEvent = new ParseJsonToEvent();

    /**
     * 广播消息
     *
     * @param messageReturnBody 消息体
     */
    @Override
    public void handleBroadcastMessage(MessageReturnBody messageReturnBody) {
        MutableText result = parseJsonToEvent.parseMessages(messageReturnBody.getMessageList());
        for (ServerPlayerEntity serverPlayer : minecraftServer.getPlayerManager().getPlayerList()) {
            serverPlayer.sendMessage(result);
        }
    }

    /**
     * 广播 Send Title 消息
     *
     * @param sendTitleReturnBody Send Title 消息体
     */
    @Override
    public void handleSendTitleMessage(SendTitleReturnBody sendTitleReturnBody) {
        // TODO Send Title Message
        logger.warn("暂未实现 Send Title Message");
    }

    /**
     * 广播 Action Bar 消息
     *
     * @param actionbarReturnBody Action Bar 消息体
     */
    @Override
    public void handleActionBarMessage(ActionbarReturnBody actionbarReturnBody) {
        // TODO Action Bar Message
        logger.warn("暂未实现 Action Bar Message");
    }
}
