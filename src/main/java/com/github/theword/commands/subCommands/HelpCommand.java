package com.github.theword.commands.subCommands;

import com.github.theword.commands.SubCommand;
import com.mojang.brigadier.Command;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.Text;

import static com.github.theword.commands.CommandManager.subCommandList;

public class HelpCommand extends SubCommand {
    @Override
    public String getName() {
        return "mcqq";
    }

    @Override
    public String getDescription() {
        return "查看 MC_QQ 命令";
    }

    @Override
    public String getSyntax() {
        return "/mcqq";
    }

    @Override
    public String getUsage() {
        return "使用：/mcqq";
    }

    public HelpCommand() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
                dispatcher.register(CommandManager.literal("mcqq")
                        .requires(source -> source.hasPermissionLevel(2))
                        .executes(
                                context -> {
                                    context.getSource().sendFeedback(() -> Text.literal("-------------------"), false);
                                    for (SubCommand subCommand : subCommandList) {
                                        context.getSource().sendFeedback(() -> Text.literal(subCommand.getUsage() + "---" + subCommand.getDescription()), false);
                                    }
                                    context.getSource().sendFeedback(() -> Text.literal("-------------------"), false);
                                    return Command.SINGLE_SUCCESS;
                                }
                        )
                )
        );
    }
}
