package me.Thelnfamous1.huskhomes.event;

import net.william278.huskhomes.event.IWarpDeleteEvent;

public class WarpDeleteEvent extends CancellableForgeHuskHomesEvent<IWarpDeleteEvent> {
    public WarpDeleteEvent(IWarpDeleteEvent wrappedEvent) {
        super(wrappedEvent);
    }

}
