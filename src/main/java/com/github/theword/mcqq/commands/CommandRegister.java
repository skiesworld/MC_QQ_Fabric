package com.github.theword.mcqq.commands;

import com.github.theword.mcqq.commands.subCommands.HelpCommand;
import com.github.theword.mcqq.commands.subCommands.ReconnectCommand;
import com.github.theword.mcqq.commands.subCommands.ReloadCommand;

import static com.github.theword.mcqq.commands.CommandManager.subCommandList;

public class CommandRegister {
    public CommandRegister() {
        subCommandList.add(new HelpCommand());
        subCommandList.add(new ReconnectCommand());
        subCommandList.add(new ReloadCommand());
    }
}
