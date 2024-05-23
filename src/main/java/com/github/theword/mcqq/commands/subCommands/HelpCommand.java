package com.github.theword.mcqq.commands.subCommands;

import com.github.theword.mcqq.commands.CommandManager;
import com.github.theword.mcqq.commands.FabricSubCommand;
import com.github.theword.mcqq.commands.SubCommand;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;


public class HelpCommand extends HelpCommandAbstract implements FabricSubCommand {


    @Override
    public int onCommand(CommandContext<ServerCommandSource> context) {
        context.getSource().sendFeedback(() -> Text.literal("-------------------"), false);
        for (SubCommand subCommand : new CommandManager().getSubCommandList()) {
            context.getSource().sendFeedback(() -> Text.literal(subCommand.getUsage() + "---" + subCommand.getDescription()), false);
        }
        context.getSource().sendFeedback(() -> Text.literal("-------------------"), false);
        return Command.SINGLE_SUCCESS;
    }
}
