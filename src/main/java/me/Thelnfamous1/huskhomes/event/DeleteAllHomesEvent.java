package me.Thelnfamous1.huskhomes.event;

import net.william278.huskhomes.event.IDeleteAllHomesEvent;

public class DeleteAllHomesEvent extends CancellableForgeHuskHomesEvent<IDeleteAllHomesEvent> {
    public DeleteAllHomesEvent(IDeleteAllHomesEvent wrappedEvent) {
        super(wrappedEvent);
    }

}
