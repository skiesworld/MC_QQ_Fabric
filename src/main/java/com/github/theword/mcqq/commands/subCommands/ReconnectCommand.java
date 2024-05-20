package com.github.theword.mcqq.commands.subCommands;

import com.github.theword.mcqq.commands.SubCommand;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;

import static com.github.theword.mcqq.utils.WebsocketManager.reconnectWebsocketClients;


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
                                    reconnectWebsocketClients(false, context);
                                    return 1;
                                }).then(CommandManager.literal("all").executes(context -> {
                                    reconnectWebsocketClients(true, context);
                                    return 1;
                                }))
                        )
                )
        );
    }
}
