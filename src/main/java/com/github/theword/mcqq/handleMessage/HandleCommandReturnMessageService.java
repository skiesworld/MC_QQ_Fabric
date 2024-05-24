package com.github.theword.mcqq.handleMessage;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class HandleCommandReturnMessageService implements HandleCommandReturnMessage {

    /**
     * @param object  命令返回者
     * @param message 返回消息
     */
    @SuppressWarnings("unchecked")
    @Override
    public void handleCommandReturnMessage(Object object, String message) {
        CommandContext<ServerCommandSource> context = (CommandContext<ServerCommandSource>) object;
        context.getSource().sendFeedback(() -> Text.literal(message), false);
    }
}
