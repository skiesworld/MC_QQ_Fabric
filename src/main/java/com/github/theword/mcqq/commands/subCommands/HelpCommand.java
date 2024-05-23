package com.github.theword.mcqq.commands.subCommands;

import com.github.theword.mcqq.commands.FabricSubCommand;
import com.github.theword.mcqq.commands.SubCommand;
import com.github.theword.mcqq.commands.subCommands.client.ReconnectCommand;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;


public class HelpCommand extends HelpCommandAbstract implements FabricSubCommand {

    private final List<SubCommand> subCommandList = new ArrayList<>();

    public HelpCommand() {
        subCommandList.add(new HelpCommand());
        subCommandList.add(new ReloadCommand());
        subCommandList.add(new ReconnectCommand());
    }

    @Override
    public int onCommand(CommandContext<ServerCommandSource> context) {
        context.getSource().sendFeedback(() -> Text.literal("-------------------"), false);
        for (SubCommand subCommand : subCommandList) {
            context.getSource().sendFeedback(() -> Text.literal(subCommand.getUsage() + "---" + subCommand.getDescription()), false);
        }
        context.getSource().sendFeedback(() -> Text.literal("-------------------"), false);
        return Command.SINGLE_SUCCESS;
    }
}
