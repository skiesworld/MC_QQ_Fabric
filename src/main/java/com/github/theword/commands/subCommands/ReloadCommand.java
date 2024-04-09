package com.github.theword.commands.subCommands;

import com.github.theword.commands.SubCommand;
import com.github.theword.constant.CommandConstantMessage;
import com.github.theword.constant.WebsocketConstantMessage;
import com.github.theword.utils.Config;
import com.github.theword.websocket.WsClient;
import com.mojang.brigadier.Command;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.Text;

import java.net.URI;
import java.net.URISyntaxException;

import static com.github.theword.utils.Tool.config;
import static com.github.theword.utils.Tool.wsClientList;


public class ReloadCommand extends SubCommand {
    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getDescription() {
        return "重载配置文件并重新连接所有 Websocket Client";
    }

    @Override
    public String getSyntax() {
        return "/mcqq reload";
    }

    @Override
    public String getUsage() {
        return "使用：/mcqq reload";
    }


    public ReloadCommand() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
                dispatcher.register(CommandManager.literal("mcqq")
                        .requires(source -> source.hasPermissionLevel(2))
                        .then(CommandManager.literal("reload")
                                .executes(context -> {
                                            config = new Config(true);
                                            context.getSource().sendFeedback(() -> Text.literal(CommandConstantMessage.RELOAD_CONFIG), false);
                                            wsClientList.forEach(wsClient -> {
                                                if (!wsClient.isClosed() && !wsClient.isClosing()) {
                                                    context.getSource().sendFeedback(() -> Text.literal(CommandConstantMessage.RELOAD_CLOSE_WEBSOCKET_CLIENT.formatted(wsClient.getURI())), false);
                                                    wsClient.close();
                                                }
                                                wsClient.getTimer().cancel();
                                            });
                                            wsClientList.clear();
                                            context.getSource().sendFeedback(() -> Text.literal(CommandConstantMessage.RELOAD_CLEAR_WEBSOCKET_CLIENT_LIST), false);
                                            config.getWebsocketUrlList().forEach(websocketUrl -> {
                                                try {
                                                    WsClient wsClient = new WsClient(new URI(websocketUrl));
                                                    wsClient.connect();
                                                    wsClientList.add(wsClient);
                                                } catch (URISyntaxException e) {
                                                    context.getSource().sendFeedback(() -> Text.literal(WebsocketConstantMessage.WEBSOCKET_ERROR_URI_SYNTAX_ERROR.formatted(websocketUrl)), false);
                                                }
                                            });
                                            context.getSource().sendFeedback(() -> Text.literal(CommandConstantMessage.RELOADED), false);
                                            return Command.SINGLE_SUCCESS;
                                        }
                                )
                        )
                )
        );
    }
}
