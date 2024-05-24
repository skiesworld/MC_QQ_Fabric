package com.github.theword.mcqq.commands;

import com.github.theword.mcqq.commands.subCommands.HelpCommand;
import com.github.theword.mcqq.commands.subCommands.ReloadCommand;
import com.github.theword.mcqq.commands.subCommands.client.ReconnectAllCommand;
import com.github.theword.mcqq.commands.subCommands.client.ReconnectCommand;
import com.github.theword.mcqq.constant.BaseConstant;
import com.mojang.brigadier.Command;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;

import static com.github.theword.mcqq.utils.Tool.handleCommandReturnMessage;

public class CommandExecutor {

    public CommandExecutor() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
                dispatcher.register(
                        CommandManager.literal(BaseConstant.COMMAND_HEADER)
                                .requires(source -> source.hasPermissionLevel(2))
                                .executes(context -> new HelpCommand().onCommand(context))
                                .then(CommandManager.literal("help")
                                        .executes(context -> new HelpCommand().onCommand(context))
                                )
                                .then(CommandManager.literal("reload")
                                        .executes(context -> new ReloadCommand().onCommand(context))
                                )
                                .then(CommandManager.literal("client")
                                        .then(CommandManager.literal("reconnect")
                                                .executes(context -> new ReconnectCommand().onCommand(context))
                                                .then(CommandManager.literal("all")
                                                        .executes(context -> new ReconnectAllCommand().onCommand(context))
                                                )
                                        )
                                )
                                .then(CommandManager.literal("server")
                                        .executes(context -> {
                                                    // TODO Websocket Server Command
                                                    handleCommandReturnMessage.handleCommandReturnMessage(context, "Server command is not supported");
                                                    return Command.SINGLE_SUCCESS;
                                                }
                                        )
                                )
                )
        );
    }
}
