package com.github.theword.mcqq.commands.subCommands.client;

import com.github.theword.mcqq.commands.FabricSubCommand;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.ServerCommandSource;

import static com.github.theword.mcqq.utils.Tool.websocketManager;

public class ReconnectAllCommand extends ReconnectCommandAbstract implements FabricSubCommand {
    /**
     * @param context CommandContext
     * @return int
     */
    @Override
    public int onCommand(CommandContext<ServerCommandSource> context) {
        websocketManager.reconnectWebsocketClients(true, context);
        return Command.SINGLE_SUCCESS;

    }
}
