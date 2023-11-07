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
import net.william278.huskhomes.ForgeHuskHomes;
import net.william278.huskhomes.user.ForgeUser;
import org.jetbrains.annotations.NotNull;

// Note that the teleport event and update player respawn position events are not handled on Fabric.
// The "update last position on teleport event" and "global respawn" features are not supported on Fabric.
public class ForgeEventListener extends EventListener {

    public ForgeEventListener(@NotNull ForgeHuskHomes plugin) {
        super(plugin);
        this.registerEvents(plugin);
    }

    // Register fabric event callback listeners to internal handlers
    private void registerEvents(@NotNull ForgeHuskHomes plugin) {
        // Join event
        /*
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> handlePlayerJoin(
                FabricUser.adapt(plugin, handler.player)
        ));
         */
        MinecraftForge.EVENT_BUS.addListener((PlayerEvent.PlayerLoggedInEvent event) -> {
            if(event.getEntity() instanceof ServerPlayer player)
                handlePlayerLeave(ForgeUser.adapt(plugin, player));
        });

        // Quit event
        /*
        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> handlePlayerLeave(
                FabricUser.adapt(plugin, handler.player)
        ));
         */
        MinecraftForge.EVENT_BUS.addListener((PlayerEvent.PlayerLoggedOutEvent event) -> {
            if(event.getEntity() instanceof ServerPlayer player){
                ForgeUser.adapt(plugin, player);
            }
        });

        // Death event
        /*
        ServerLivingEntityEvents.AFTER_DEATH.register((entity, source) -> {
            if (entity instanceof ServerPlayerEntity player) {
                handlePlayerDeath(FabricUser.adapt(plugin, player));
            }
        });
         */
        MinecraftForge.EVENT_BUS.addListener(EventPriority.LOWEST, (LivingDeathEvent event) -> {
            if(!event.isCanceled() && event.getEntity() instanceof ServerPlayer player)
                handlePlayerDeath(ForgeUser.adapt(plugin, player));
        });

        // Respawn event
        /*
        ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> handlePlayerRespawn(
                FabricUser.adapt(plugin, newPlayer)
        ));
         */
        MinecraftForge.EVENT_BUS.addListener((PlayerEvent.PlayerRespawnEvent event) -> {
            if(event.getEntity() instanceof ServerPlayer player){
                handlePlayerRespawn(ForgeUser.adapt(plugin, player));
            }
        });
    }

}
