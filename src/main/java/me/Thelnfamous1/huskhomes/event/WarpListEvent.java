package me.Thelnfamous1.huskhomes.event;

import net.william278.huskhomes.event.IWarpListEvent;

public class WarpListEvent extends CancellableForgeHuskHomesEvent<IWarpListEvent> {
    public WarpListEvent(IWarpListEvent wrappedEvent) {
        super(wrappedEvent);
    }

}
