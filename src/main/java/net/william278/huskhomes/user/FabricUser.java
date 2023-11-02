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

package net.william278.huskhomes.user;

import io.netty.buffer.Unpooled;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.kyori.adventure.audience.Audience;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundCustomPayloadPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.william278.huskhomes.FabricHuskHomes;
import net.william278.huskhomes.position.Location;
import net.william278.huskhomes.position.Position;
import net.william278.huskhomes.position.World;
import net.william278.huskhomes.teleport.TeleportationException;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class FabricUser extends OnlineUser {
    private final FabricHuskHomes plugin;
    private final ServerPlayer player;

    private FabricUser(@NotNull FabricHuskHomes plugin, @NotNull ServerPlayer player) {
        super(player.getUUID(), player.getScoreboardName());
        this.plugin = plugin;
        this.player = player;
    }

    @NotNull
    public static FabricUser adapt(@NotNull FabricHuskHomes plugin, @NotNull ServerPlayer player) {
        return new FabricUser(plugin, player);
    }

    @Override
    public Position getPosition() {
        return Position.at(
                player.getX(), player.getY(), player.getZ(),
                player.getYRot(), player.getXRot(),
                World.from(
                        player.getLevel().dimension().location().toString(),
                        UUID.nameUUIDFromBytes(player.getLevel().dimension().location().toString().getBytes())
                ),
                plugin.getServerName()
        );
    }

    @Override
    public Optional<Position> getBedSpawnPosition() {
        final BlockPos spawn = player.getRespawnPosition();
        if (spawn == null) {
            return Optional.empty();
        }

        return Optional.of(Position.at(
                spawn.getX(), spawn.getY(), spawn.getZ(),
                player.getRespawnAngle(), 0,
                World.from(
                        player.getRespawnDimension().location().toString(),
                        UUID.nameUUIDFromBytes(player.getRespawnDimension().location().toString().getBytes())
                ),
                plugin.getServerName()
        ));
    }

    @Override
    public double getHealth() {
        return player.getHealth();
    }

    @Override
    public boolean hasPermission(@NotNull String node) {
        final boolean requiresOp = Boolean.TRUE.equals(plugin.getPermissions().getOrDefault(node, true));
        return Permissions.check(player, node, !requiresOp || player.hasPermissions(3));
    }

    @Override
    @NotNull
    public Map<String, Boolean> getPermissions() {
        return plugin.getPermissions().entrySet().stream()
                .filter(entry -> Permissions.check(player, entry.getKey(), entry.getValue()))
                .collect(HashMap::new, (map, entry) -> map.put(entry.getKey(), entry.getValue()), HashMap::putAll);
    }

    @Override
    @NotNull
    protected List<Integer> getNumericalPermissions(@NotNull String nodePrefix) {
        final List<Integer> permissions = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            if (hasPermission(nodePrefix + i)) {
                permissions.add(i);
            }
        }
        return permissions.stream().sorted(Collections.reverseOrder()).toList();
    }

    @Override
    @NotNull
    public Audience getAudience() {
        return (Audience) player; // Adventure's Fabric platform mixins Audience into ServerPlayer
    }

    @Override
    public void teleportLocally(@NotNull Location location, boolean asynchronous) throws TeleportationException {
        final MinecraftServer server = player.getLevel().getServer();
        final ResourceLocation worldId = ResourceLocation.tryParse(location.getWorld().getName());
        //noinspection ConstantConditions
        player.teleportTo(
                server.getLevel(server.levelKeys().stream()
                        .filter(key -> key.location().equals(worldId)).findFirst()
                        .orElseThrow(() -> new TeleportationException(TeleportationException.Type.WORLD_NOT_FOUND))
                ),
                location.getX(), location.getY(), location.getZ(),
                location.getYaw(), location.getPitch()
        );
    }

    @Override
    public void sendPluginMessage(@NotNull String channel, byte[] message) {
        final FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        buf.writeBytes(message);
        final ClientboundCustomPayloadPacket packet = new ClientboundCustomPayloadPacket(parseIdentifier(channel), buf);
        player.connection.send(packet);
    }

    @Override
    public boolean isMoving() {
        return player.isInWater() || player.isFallFlying() || player.isSprinting() || player.isDiscrete();
    }

    @Override
    public boolean isVanished() {
        return false;
    }

    @NotNull
    private static ResourceLocation parseIdentifier(@NotNull String channel) {
        if (channel.equals("BungeeCord")) {
            return new ResourceLocation("bungeecord", "main");
        }
        return Optional.ofNullable(ResourceLocation.tryParse(channel))
                .orElseThrow(() -> new IllegalArgumentException("Invalid channel name: " + channel));
    }

}
