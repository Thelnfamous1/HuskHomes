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

package net.william278.huskhomes.listener;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.william278.huskhomes.FabricHuskHomes;
import net.william278.huskhomes.user.FabricUser;
import org.jetbrains.annotations.NotNull;

// Note that the teleport event and update player respawn position events are not handled on Fabric.
// The "update last position on teleport event" and "global respawn" features are not supported on Fabric.
public class FabricEventListener extends EventListener {

    public FabricEventListener(@NotNull FabricHuskHomes plugin) {
        super(plugin);
        this.registerEvents(plugin);
    }

    // Register fabric event callback listeners to internal handlers
    private void registerEvents(@NotNull FabricHuskHomes plugin) {
        // Join event
        /*
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> handlePlayerJoin(
                FabricUser.adapt(handler.player, plugin)
        ));
         */
        MinecraftForge.EVENT_BUS.addListener((PlayerEvent.PlayerLoggedInEvent event) -> {
            if(event.getEntity() instanceof ServerPlayer player)
                handlePlayerJoin(FabricUser.adapt(player, plugin));
        });

        // Quit event
        /*
        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> handlePlayerLeave(
                FabricUser.adapt(handler.player, plugin)
        ));
         */
        MinecraftForge.EVENT_BUS.addListener((PlayerEvent.PlayerLoggedOutEvent event) -> {
            if(event.getEntity() instanceof ServerPlayer player){
                handlePlayerLeave(FabricUser.adapt(player, plugin));
            }
        });

        // Death event
        /*
        ServerLivingEntityEvents.AFTER_DEATH.register((entity, source) -> {
            if (entity instanceof ServerPlayerEntity player) {
                handlePlayerDeath(FabricUser.adapt(player, plugin));
            }
        });
         */
        MinecraftForge.EVENT_BUS.addListener(EventPriority.LOWEST, (LivingDeathEvent event) -> {
            if(!event.isCanceled() && event.getEntity() instanceof ServerPlayer player)
                handlePlayerDeath(FabricUser.adapt(player, plugin));
        });

        // Respawn event
        /*
        ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> handlePlayerRespawn(
                FabricUser.adapt(newPlayer, plugin)
        ));
         */
        MinecraftForge.EVENT_BUS.addListener((PlayerEvent.PlayerRespawnEvent event) -> {
            if(event.getEntity() instanceof ServerPlayer player){
                handlePlayerRespawn(FabricUser.adapt(player, plugin));
            }
        });
    }

}
