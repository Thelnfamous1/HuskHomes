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

import me.Thelnfamous1.huskhomes.event.DeleteAllWarpsEvent;
import me.Thelnfamous1.huskhomes.event.ForgeHuskHomesEvent;
import net.william278.huskhomes.user.CommandUser;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public interface DeleteAllWarpsCallback extends FabricEventCallback<IDeleteAllWarpsEvent> {

    /*
    @NotNull
    Event<DeleteAllWarpsCallback> EVENT = EventFactory.createArrayBacked(DeleteAllWarpsCallback.class,
            (listeners) -> (event) -> {
                for (DeleteAllWarpsCallback listener : listeners) {
                    final InteractionResult result = listener.invoke(event);
                    if (event.isCancelled()) {
                        return InteractionResult.CONSUME;
                    } else if (result != InteractionResult.PASS) {
                        event.setCancelled(true);
                        return result;
                    }
                }

                return InteractionResult.PASS;
            });
     */

    @NotNull
    Function<CommandUser, IDeleteAllWarpsEvent> SUPPLIER = (deleter) ->
            new IDeleteAllWarpsEvent() {
                private boolean cancelled = false;

                @Override
                @NotNull
                public CommandUser getDeleter() {
                    return deleter;
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
                public ForgeHuskHomesEvent<IDeleteAllWarpsEvent> getEvent() {
                    return new DeleteAllWarpsEvent(this);
                }
            };

}
