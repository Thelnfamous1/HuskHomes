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

package net.william278.huskhomes.event;

import net.william278.huskhomes.HuskHomes;
import net.william278.huskhomes.position.Home;
import net.william278.huskhomes.position.Position;
import net.william278.huskhomes.position.Warp;
import net.william278.huskhomes.teleport.Teleport;
import net.william278.huskhomes.teleport.TeleportRequest;
import net.william278.huskhomes.teleport.TimedTeleport;
import net.william278.huskhomes.user.CommandUser;
import net.william278.huskhomes.user.OnlineUser;
import net.william278.huskhomes.user.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

/**
 * An abstract dispatcher of events
 */
public interface EventDispatcher {

    /**
     * Fire an event synchronously, then run a callback asynchronously
     *
     * @param event    The event to fire
     * @param callback The callback to run after the event has been fired
     * @param <T>      The type of event to fire
     */
    default <T extends HuskHomesEvent> void fireEvent(@NotNull T event, @Nullable Consumer<T> callback) {
        getPlugin().runSync(() -> {
            if (!fireIsCancelled(event) && callback != null) {
                getPlugin().runAsync(() -> callback.accept(event));
            }
        });
    }

    /**
     * Fire an event on this thread, and return whether the event was canceled
     *
     * @param event The event to fire
     * @param <T>   The type of event to fire
     * @return Whether the event was canceled
     */
    <T extends HuskHomesEvent> boolean fireIsCancelled(@NotNull T event);

    ITeleportEvent getTeleportEvent(@NotNull Teleport teleport);

    ITeleportWarmupEvent getTeleportWarmupEvent(@NotNull TimedTeleport teleport, int duration);

    ISendTeleportRequestEvent getSendTeleportRequestEvent(@NotNull OnlineUser sender, @NotNull TeleportRequest request);

    IReceiveTeleportRequestEvent getReceiveTeleportRequestEvent(@NotNull OnlineUser recipient, @NotNull TeleportRequest request);

    IReplyTeleportRequestEvent getReplyTeleportRequestEvent(@NotNull OnlineUser recipient, @NotNull TeleportRequest request);

    IHomeCreateEvent getHomeCreateEvent(@NotNull User owner, @NotNull String name, @NotNull Position position, @NotNull CommandUser creator);

    IHomeEditEvent getHomeEditEvent(@NotNull Home home, @NotNull CommandUser editor);

    IHomeDeleteEvent getHomeDeleteEvent(@NotNull Home home, @NotNull CommandUser deleter);

    IWarpCreateEvent getWarpCreateEvent(@NotNull String name, @NotNull Position position, @NotNull CommandUser creator);

    IWarpEditEvent getWarpEditEvent(@NotNull Warp warp, @NotNull CommandUser editor);

    IWarpDeleteEvent getWarpDeleteEvent(@NotNull Warp warp, @NotNull CommandUser deleter);

    IHomeListEvent getViewHomeListEvent(@NotNull List<Home> homes, @NotNull CommandUser listViewer, boolean publicHomeList);

    IWarpListEvent getViewWarpListEvent(@NotNull List<Warp> homes, @NotNull CommandUser listViewer);

    IDeleteAllHomesEvent getDeleteAllHomesEvent(@NotNull User user, @NotNull CommandUser deleter);

    IDeleteAllWarpsEvent getDeleteAllWarpsEvent(@NotNull CommandUser deleter);

    @NotNull
    HuskHomes getPlugin();

}
