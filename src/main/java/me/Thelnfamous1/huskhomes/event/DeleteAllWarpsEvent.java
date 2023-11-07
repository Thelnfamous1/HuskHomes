package me.Thelnfamous1.huskhomes.event;

import net.william278.huskhomes.event.IDeleteAllWarpsEvent;

public class DeleteAllWarpsEvent extends CancellableForgeHuskHomesEvent<IDeleteAllWarpsEvent> {
    public DeleteAllWarpsEvent(IDeleteAllWarpsEvent wrappedEvent) {
        super(wrappedEvent);
    }

}
