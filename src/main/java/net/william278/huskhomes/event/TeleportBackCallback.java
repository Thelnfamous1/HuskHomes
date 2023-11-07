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

import me.Thelnfamous1.huskhomes.event.ForgeHuskHomesEvent;
import me.Thelnfamous1.huskhomes.event.TeleportBackEvent;
import net.william278.huskhomes.position.Position;
import net.william278.huskhomes.teleport.Teleport;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public interface TeleportBackCallback extends FabricEventCallback<ITeleportBackEvent> {

    /*
    @NotNull
    Event<TeleportBackCallback> EVENT = EventFactory.createArrayBacked(TeleportBackCallback.class,
            (listeners) -> (event) -> {
                for (TeleportBackCallback listener : listeners) {
                    final InteractionResult result = listener.invoke(event);
                    if (event.isCancelled()) {
                        return InteractionResult.FAIL;
                    } else if (result == InteractionResult.FAIL) {
                        event.setCancelled(true);
                        return result;
                    }
                }

                return InteractionResult.PASS;
            });
     */

    @NotNull
    Function<Teleport, ITeleportBackEvent> SUPPLIER = (teleport) ->
            new ITeleportBackEvent() {
                private boolean cancelled = false;

                @Override
                @NotNull
                public Position getLastPosition() {
                    return (Position) getTeleport().getTarget();
                }

                @Override
                @NotNull
                public Teleport getTeleport() {
                    return teleport;
                }

                @Override
                public void setCancelled(boolean cancelled) {
                    this.cancelled = cancelled;
                }

                @Override
                public boolean isCancelled() {
                    return cancelled;
                }

                @NotNull
                public ForgeHuskHomesEvent<ITeleportBackEvent> getEvent() {
                    return new TeleportBackEvent(this);
                }

            };

}
