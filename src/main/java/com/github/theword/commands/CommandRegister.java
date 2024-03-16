package com.github.theword.commands;

import com.github.theword.commands.subCommands.HelpCommand;
import com.github.theword.commands.subCommands.ReconnectCommand;
import com.github.theword.commands.subCommands.ReloadCommand;

import static com.github.theword.commands.CommandManager.subCommandList;

public class CommandRegister {
    public CommandRegister() {
        subCommandList.add(new HelpCommand());
        subCommandList.add(new ReconnectCommand());
        subCommandList.add(new ReloadCommand());
    }
}
