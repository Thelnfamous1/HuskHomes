/*
 * This file is part of HuskHomes, licensed under the Apache License 2.0.
 *
 *  Copyright (c) William278 <will27528@gmail.com>
 *  Copyright (c) contributors
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package net.william278.huskhomes.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import me.infamous.permissions.PermissionCheckEvent;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.luckperms.api.util.Tristate;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.william278.huskhomes.ForgeHuskHomes;
import net.william278.huskhomes.teleport.TeleportRequest;
import net.william278.huskhomes.user.CommandUser;
import net.william278.huskhomes.user.ForgeUser;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.function.Predicate;

import static com.mojang.brigadier.arguments.StringArgumentType.greedyString;
import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

public class FabricCommand {

    private final ForgeHuskHomes plugin;
    private final Command command;

    public FabricCommand(@NotNull Command command, @NotNull ForgeHuskHomes plugin) {
        this.command = command;
        this.plugin = plugin;
    }

    public void register(@NotNull CommandDispatcher<CommandSourceStack> dispatcher) {
        // Register brigadier command
        final Predicate<CommandSourceStack> predicate = Permissions
                .require(command.getPermission(), command.isOperatorCommand() ? 3 : 0);
        final LiteralArgumentBuilder<CommandSourceStack> builder = literal(command.getName())
                .requires(predicate).executes(getBrigadierExecutor());
        plugin.getPermissions().put(command.getPermission(), command.isOperatorCommand());
        if (!command.getRawUsage().isBlank()) {
            builder.then(argument(command.getRawUsage().replaceAll("[<>\\[\\]]", ""), greedyString())
                    .executes(getBrigadierExecutor())
                    .suggests(getBrigadierSuggester()));
        }

        // Register additional permissions
        MinecraftForge.EVENT_BUS.addListener((PermissionCheckEvent event) -> {
            final Map<String, Boolean> permissions = this.command.getAdditionalPermissions();
            permissions.forEach((permission, isOp) -> this.plugin.getPermissions().put(permission, isOp));
            if (permissions.containsKey(event.getPermission()) && permissions.get(event.getPermission()) && event.getSource().hasPermission(3)) {
                event.setState(Tristate.TRUE);
            }
        });

        // Register aliases
        final LiteralCommandNode<CommandSourceStack> node = dispatcher.register(builder);
        dispatcher.register(literal("huskhomes:" + command.getName())
                .requires(predicate).executes(getBrigadierExecutor()).redirect(node));
        command.getAliases().forEach(alias -> dispatcher.register(literal(alias)
                .requires(predicate).executes(getBrigadierExecutor()).redirect(node)));
    }

    private com.mojang.brigadier.Command<CommandSourceStack> getBrigadierExecutor() {
        return (context) -> {
            command.onExecuted(
                    resolveExecutor(context.getSource()),
                    command.removeFirstArg(context.getInput().split(" "))
            );
            return 1;
        };
    }

    private com.mojang.brigadier.suggestion.SuggestionProvider<CommandSourceStack> getBrigadierSuggester() {
        if (!(command instanceof TabProvider provider)) {
            return (context, builder) -> com.mojang.brigadier.suggestion.Suggestions.empty();
        }
        return (context, builder) -> {
            final String[] args = command.removeFirstArg(context.getInput().split(" "));
            provider.getSuggestions(resolveExecutor(context.getSource()), args).stream()
                    .map(suggestion -> {
                        final String completedArgs = String.join(" ", args);
                        int lastIndex = completedArgs.lastIndexOf(" ");
                        if (lastIndex == -1) {
                            return suggestion;
                        }
                        return completedArgs.substring(0, lastIndex + 1) + suggestion;
                    })
                    .forEach(builder::suggest);
            return builder.buildFuture();
        };
    }

    private CommandUser resolveExecutor(@NotNull CommandSourceStack source) {
        if (source.getEntity() instanceof ServerPlayer player) {
            return ForgeUser.adapt(plugin, player);
        }
        return plugin.getConsole();
    }


    /**
     * Commands available on the Fabric HuskHomes implementation
     */
    public enum Type {
        HOME_COMMAND(new PrivateHomeCommand(ForgeHuskHomes.getInstance())),
        SET_HOME_COMMAND(new SetHomeCommand(ForgeHuskHomes.getInstance())),
        HOME_LIST_COMMAND(new PrivateHomeListCommand(ForgeHuskHomes.getInstance())),
        DEL_HOME_COMMAND(new DelHomeCommand(ForgeHuskHomes.getInstance())),
        EDIT_HOME_COMMAND(new EditHomeCommand(ForgeHuskHomes.getInstance())),
        PUBLIC_HOME_COMMAND(new PublicHomeCommand(ForgeHuskHomes.getInstance())),
        PUBLIC_HOME_LIST_COMMAND(new PublicHomeListCommand(ForgeHuskHomes.getInstance())),
        WARP_COMMAND(new WarpCommand(ForgeHuskHomes.getInstance())),
        SET_WARP_COMMAND(new SetWarpCommand(ForgeHuskHomes.getInstance())),
        WARP_LIST_COMMAND(new WarpListCommand(ForgeHuskHomes.getInstance())),
        DEL_WARP_COMMAND(new DelWarpCommand(ForgeHuskHomes.getInstance())),
        EDIT_WARP_COMMAND(new EditWarpCommand(ForgeHuskHomes.getInstance())),
        TP_COMMAND(new TpCommand(ForgeHuskHomes.getInstance())),
        TP_HERE_COMMAND(new TpHereCommand(ForgeHuskHomes.getInstance())),
        TPA_COMMAND(new TeleportRequestCommand(ForgeHuskHomes.getInstance(), TeleportRequest.Type.TPA)),
        TPA_HERE_COMMAND(new TeleportRequestCommand(ForgeHuskHomes.getInstance(), TeleportRequest.Type.TPA_HERE)),
        TPACCEPT_COMMAND(new TpRespondCommand(ForgeHuskHomes.getInstance(), true)),
        TPDECLINE_COMMAND(new TpRespondCommand(ForgeHuskHomes.getInstance(), false)),
        RTP_COMMAND(new RtpCommand(ForgeHuskHomes.getInstance())),
        TP_IGNORE_COMMAND(new TpIgnoreCommand(ForgeHuskHomes.getInstance())),
        TP_OFFLINE_COMMAND(new TpOfflineCommand(ForgeHuskHomes.getInstance())),
        TP_ALL_COMMAND(new TpAllCommand(ForgeHuskHomes.getInstance())),
        TPA_ALL_COMMAND(new TpaAllCommand(ForgeHuskHomes.getInstance())),
        SPAWN_COMMAND(new SpawnCommand(ForgeHuskHomes.getInstance())),
        SET_SPAWN_COMMAND(new SetSpawnCommand(ForgeHuskHomes.getInstance())),
        BACK_COMMAND(new BackCommand(ForgeHuskHomes.getInstance())),
        HUSKHOMES_COMMAND(new HuskHomesCommand(ForgeHuskHomes.getInstance()));

        private final Command command;

        Type(@NotNull Command command) {
            this.command = command;
        }

        @NotNull
        public Command getCommand() {
            return command;
        }
    }

}
