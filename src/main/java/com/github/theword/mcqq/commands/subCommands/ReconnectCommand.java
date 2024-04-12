package com.github.theword.mcqq.commands.subCommands;

import com.github.theword.mcqq.commands.SubCommand;
import com.github.theword.mcqq.constant.CommandConstantMessage;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.Text;

import java.util.concurrent.atomic.AtomicInteger;

import static com.github.theword.mcqq.utils.Tool.wsClientList;


public class ReconnectCommand extends SubCommand {
    @Override
    public String getName() {
        return "reconnect";
    }

    @Override
    public String getDescription() {
        return "重新连接 Websocket Clients.";
    }

    @Override
    public String getSyntax() {
        return "/mcqq reconnect";
    }

    @Override
    public String getUsage() {
        return "使用：/mcqq reconnect [all]";
    }

    public ReconnectCommand() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
                dispatcher.register(CommandManager.literal("mcqq")
                        .requires(source -> source.hasPermissionLevel(2))
                        .then(CommandManager.literal("reconnect")
                                .executes(context -> {
                                    context.getSource().sendFeedback(() -> Text.literal(CommandConstantMessage.RECONNECT_NOT_OPEN_CLIENT), false);
                                    AtomicInteger opened = new AtomicInteger();
                                    wsClientList.forEach(wsClient -> {
                                        if (!wsClient.isOpen()) {
                                            wsClient.reconnectWebsocket();
                                            context.getSource().sendFeedback(() -> Text.literal(CommandConstantMessage.RECONNECT_MESSAGE.formatted(wsClient.getURI())), false);
                                        } else {
                                            opened.getAndIncrement();
                                        }
                                    });
                                    if (opened.get() == wsClientList.size()) {
                                        context.getSource().sendFeedback(() -> Text.literal(CommandConstantMessage.RECONNECT_NO_CLIENT_NEED_RECONNECT), false);
                                    }
                                    context.getSource().sendFeedback(() -> Text.literal(CommandConstantMessage.RECONNECTED), false);
                                    return 1;
                                }).then(CommandManager.literal("all").executes(context -> {
                                    context.getSource().sendFeedback(() -> Text.literal(CommandConstantMessage.RECONNECT_ALL_CLIENT), false);
                                    wsClientList.forEach(wsClient -> {
                                        wsClient.reconnectWebsocket();
                                        context.getSource().sendFeedback(() -> Text.literal(CommandConstantMessage.RECONNECT_MESSAGE.formatted(wsClient.getURI())), false);
                                    });
                                    context.getSource().sendFeedback(() -> Text.literal(CommandConstantMessage.RECONNECTED), false);
                                    return 1;
                                }))
                        )
                )
        );
    }
}
